package com.trading.demo.auth.application.usecase;

import com.trading.demo.auth.application.dto.AuthResponse;
import com.trading.demo.auth.application.dto.LoginRequest;
import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.domain.repository.RefreshTokenRepository;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.auth.infrastructure.persistence.security.JwtProvider;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.exception.ErrorCode;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;

    public AuthResponse execute(LoginRequest request) {

        // 1. find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UUID userId = user.getId();

        // 2. check status
        if (!user.isActive()) {
            throw new AppException(ErrorCode.USER_INACTIVE);
        }

        // 3. check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        List<String> roles = roleRepository.findRoleByUserId(userId);
        // 4. create access token (JWT)
        String accessToken = jwtProvider.generateToken(userId, roles);

        // 5. create refresh token (save DB)
        String refreshTokenValue = jwtProvider.generateRefreshToken();

        RefreshToken refreshToken = RefreshToken.create(
                userId,
                refreshTokenValue
        );

        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .build();
    }
}

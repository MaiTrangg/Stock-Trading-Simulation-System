package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.dto.AuthResponse;
import com.trading.stock_trading_system.auth.application.dto.LoginRequest;
import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.domain.repository.RefreshTokenRepository;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.auth.infrastructure.persistence.security.JwtProvider;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. check status
        if (!user.isActive()) {
            throw new RuntimeException("User is not active");
        }

        // 3. check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        List<String> roles = roleRepository.findRoleByUserId(user.getId());
        // 4. create access token (JWT)
        String accessToken = jwtProvider.generateToken(user.getId(), roles);

        // 5. create refresh token (save DB)
        String refreshTokenValue = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.create(
                user.getId(),
                refreshTokenValue
        );

        refreshTokenRepository.save(refreshToken);

        // 6. return response
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .build();
    }
}

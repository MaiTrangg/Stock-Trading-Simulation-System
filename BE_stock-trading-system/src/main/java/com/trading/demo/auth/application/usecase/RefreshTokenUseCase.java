package com.trading.demo.auth.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.auth.application.dto.response.AuthResponse;
import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.domain.repository.RefreshTokenRepository;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.auth.infrastructure.security.JwtProvider;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private RefreshTokenRepository refreshTokenRepository;
    private JwtProvider jwtProvider;
    private RoleRepository roleRepository;

    public AuthResponse execute(String refreshTokenStr) {
        // 1. find refresh-token
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(refreshTokenStr)
                        .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));

        UUID userId = refreshToken.getUserId();

        // 2. check valid refresh token
        if (!refreshToken.isValid()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        // 3. generate new access token
        List<String> roles = roleRepository.findRoleByUserId(userId);
        String newAccessToken = jwtProvider.generateToken(userId, roles);

        // 4. revoke old refresh token
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        // .5 generate new refresh token and save DB
        RefreshToken newRefreshToken = RefreshToken.create(userId, jwtProvider.generateRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
}

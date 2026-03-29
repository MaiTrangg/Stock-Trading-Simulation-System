package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.dto.AuthResponse;
import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.domain.repository.RefreshTokenRepository;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.auth.infrastructure.persistence.mapper.RefreshTokenMapper;
import com.trading.stock_trading_system.auth.infrastructure.persistence.security.JwtProvider;
import com.trading.stock_trading_system.common.exception.AppException;
import com.trading.stock_trading_system.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private RefreshTokenRepository refreshTokenRepository;
    private JwtProvider jwtProvider;
    private RoleRepository roleRepository;

    public AuthResponse execute(String refreshTokenStr){
        //1. find refresh-token
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(()-> new AppException(ErrorCode.TOKEN_NOT_FOUND));

        UUID userId = refreshToken.getUserId();

        //2. check valid refresh token
        if(!refreshToken.isValid()) throw new AppException(ErrorCode.INVALID_TOKEN);

        //3. generate new access token
        List<String> roles = roleRepository.findRoleByUserId(userId);
        String newAccessToken = jwtProvider.generateToken(userId,roles);

        //4. revoke old refresh token
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        //.5 generate new refresh token and save DB
        RefreshToken newRefreshToken = RefreshToken.create(userId, jwtProvider.generateRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();

    }
}

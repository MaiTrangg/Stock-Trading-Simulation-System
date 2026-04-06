package com.trading.demo.auth.application.usecase;

import org.springframework.stereotype.Service;

import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.domain.repository.RefreshTokenRepository;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenRepository refreshRepo;

    public void execute(String token) {

        // 1. find refreshToken in DB
        RefreshToken refreshToken =
                refreshRepo
                        .findByToken(token)
                        .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));

        // 2. check valid
        if (!refreshToken.isValid()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        // 3. if isValid = True -> revoke token
        refreshRepo.revoke(token);
    }
}

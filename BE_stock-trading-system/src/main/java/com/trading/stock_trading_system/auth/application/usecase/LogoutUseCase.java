package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.domain.repository.RefreshTokenRepository;
import com.trading.stock_trading_system.common.exception.AppException;
import com.trading.stock_trading_system.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenRepository refreshRepo;

    public void execute(String token) {

        RefreshToken refreshToken = refreshRepo.findByToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));

        if (!refreshToken.isValid()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        refreshRepo.revoke(token);
    }
}

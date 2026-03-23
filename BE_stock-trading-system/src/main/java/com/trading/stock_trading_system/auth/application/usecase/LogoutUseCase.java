package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenRepository refreshRepo;

    public void execute(String token) {

        RefreshToken refreshToken = refreshRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (!refreshToken.isValid()) {
            throw new RuntimeException("Invalid token");
        }

        refreshRepo.revoke(token);
    }
}

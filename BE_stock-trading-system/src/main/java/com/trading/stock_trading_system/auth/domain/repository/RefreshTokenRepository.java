package com.trading.stock_trading_system.auth.domain.repository;

import com.trading.stock_trading_system.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    void revoke(String token);
}

package com.trading.demo.auth.domain.repository;

import com.trading.demo.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    void revoke(String token);
}

package com.trading.demo.auth.domain.repository;

import java.util.Optional;

import com.trading.demo.auth.domain.model.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    void revoke(String token);
}

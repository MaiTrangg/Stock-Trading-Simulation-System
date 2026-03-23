package com.trading.stock_trading_system.auth.infrastructure.persistence.repository;

import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.domain.repository.RefreshTokenRepository;
import com.trading.stock_trading_system.auth.infrastructure.persistence.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpaRepository;
    private final RefreshTokenMapper mapper;

    @Override
    public void save(RefreshToken token) {
        jpaRepository.save(mapper.toEntity(token));
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(mapper::toDomain);
    }

    @Override
    public void revoke(String token) {
        jpaRepository.revoke(token);
    }
}

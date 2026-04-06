package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.domain.repository.RefreshTokenRepository;
import com.trading.demo.auth.infrastructure.persistence.mapper.RefreshTokenMapper;

import lombok.RequiredArgsConstructor;

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
        return jpaRepository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public void revoke(String token) {
        jpaRepository.revoke(token);
    }
}

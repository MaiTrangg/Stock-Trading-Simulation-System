package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.auth.infrastructure.persistence.entity.RefreshTokenEntity;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    @Query(
            "UPDATE RefreshTokenEntity r SET r.revoked = true WHERE r.token = :token AND r.revoked = false")
    void revoke(@Param("token") String token);
}

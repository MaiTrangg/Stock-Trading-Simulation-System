package com.trading.stock_trading_system.user.infrastructure.persistence.repository;

import com.trading.stock_trading_system.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("""
    UPDATE UserEntity u
    SET u.status = 'ACTIVE'
    WHERE u.id = :id
""")
    void activateUser(UUID id);
}

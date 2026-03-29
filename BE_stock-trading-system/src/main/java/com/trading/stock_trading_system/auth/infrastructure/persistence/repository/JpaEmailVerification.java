package com.trading.stock_trading_system.auth.infrastructure.persistence.repository;

import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.infrastructure.persistence.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface JpaEmailVerification extends JpaRepository<EmailVerificationEntity, UUID> {
    Optional<EmailVerificationEntity> findByUserIdAndIsDeleteFalse(UUID userId);
    @Modifying
    @Query("""
    UPDATE EmailVerificationEntity e
    SET e.isDelete = true
    WHERE e.userId = :userId AND e.isDelete = false
""")
    void softDeleteByUserId(UUID userId);

    @Modifying
    @Query("""
    UPDATE EmailVerificationEntity e
    SET e.attemptCount = e.attemptCount + 1
    WHERE e.id = :id
""")
    void increaseAttempt(UUID id);

    @Modifying
    @Query("""
    UPDATE EmailVerificationEntity e
    SET e.verified = true,
        e.isDelete = true
    WHERE e.id = :id
""")
    void verifyOtp(UUID id);
}

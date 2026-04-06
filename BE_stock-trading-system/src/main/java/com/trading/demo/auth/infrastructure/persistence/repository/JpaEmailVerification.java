package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.auth.domain.enums.OtpStatus;
import com.trading.demo.auth.domain.enums.OtpType;
import com.trading.demo.auth.infrastructure.persistence.entity.EmailVerificationEntity;

public interface JpaEmailVerification extends JpaRepository<EmailVerificationEntity, UUID> {
    @Transactional
    @Query(
            "SELECT e FROM EmailVerificationEntity e "
                    + "WHERE e.userId = :userId AND e.token = :token AND e.status = 'ACTIVE'")
    Optional<EmailVerificationEntity> findActiveOtpByToken(
            @Param("userId") UUID userId, @Param("token") String token);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying(clearAutomatically = true)
    @Query(
            """
                        UPDATE EmailVerificationEntity e
                        SET e.verifyAttempts = e.verifyAttempts + 1
                        WHERE e.id = :id
                    """)
    void increaseAttempt(@Param("id") UUID id);

    Optional<EmailVerificationEntity> findByUserIdAndStatusAndAndType(UUID userId, OtpStatus status, OtpType type);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("UPDATE EmailVerificationEntity e SET e.status = 'EXPIRED' WHERE e.id = :id")
    void markExpired(@Param("id") UUID id);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("UPDATE EmailVerificationEntity e SET e.status = 'VERIFIED' WHERE e.id = :id")
    void markVerified(@Param("id") UUID id);
}

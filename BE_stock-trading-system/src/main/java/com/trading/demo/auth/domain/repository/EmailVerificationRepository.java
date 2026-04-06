package com.trading.demo.auth.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.trading.demo.auth.domain.model.EmailVerification;

public interface EmailVerificationRepository {

    Optional<EmailVerification> findActiveOtp(UUID userId);

    Optional<EmailVerification> findActiveOtpByToken(UUID userId, String token);

    void save(EmailVerification ev);

    void markExpired(UUID id);

    void markVerified(UUID id);

    void increaseAttempt(UUID id);
}

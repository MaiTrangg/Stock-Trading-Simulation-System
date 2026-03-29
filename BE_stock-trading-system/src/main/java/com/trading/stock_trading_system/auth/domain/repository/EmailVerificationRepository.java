package com.trading.stock_trading_system.auth.domain.repository;

import com.trading.stock_trading_system.auth.domain.model.EmailVerification;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository {

    void save(EmailVerification ev);

    Optional<EmailVerification> findByUserIdAndIsDeleteFalse(UUID userId);
    void softDeleteByUserId(UUID userId);

    void increaseAttempt(UUID id);

   void  verifyOtp(UUID id);
}

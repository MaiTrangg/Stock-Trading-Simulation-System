package com.trading.stock_trading_system.auth.infrastructure.persistence.repository;

import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.domain.repository.EmailVerificationRepository;
import com.trading.stock_trading_system.auth.infrastructure.persistence.mapper.EmailVerificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {
    private final JpaEmailVerification jpaEmailVerification;
    private final EmailVerificationMapper emailVerificationMapper;

    @Override
    public void save(EmailVerification ev) {
       jpaEmailVerification.save(emailVerificationMapper.toEntity(ev));
    }

    @Override
    public Optional<EmailVerification> findByUserIdAndIsDeleteFalse(UUID userId) {
        return jpaEmailVerification.findByUserIdAndIsDeleteFalse(userId).map(emailVerificationMapper::toDomain);
    }

    @Override
    public void softDeleteByUserId(UUID userId) {
      jpaEmailVerification.softDeleteByUserId(userId);
    }

    @Override
    @Transactional
    public void increaseAttempt(UUID id) {
        jpaEmailVerification.increaseAttempt(id);
    }

    @Override
    public void verifyOtp(UUID id) {
        jpaEmailVerification.verifyOtp(id);
    }
}

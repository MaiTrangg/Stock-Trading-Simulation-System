package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.auth.domain.enums.OtpStatus;
import com.trading.demo.auth.domain.enums.OtpType;
import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.domain.repository.EmailVerificationRepository;
import com.trading.demo.auth.infrastructure.persistence.mapper.EmailVerificationMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerification jpaEmailVerification;
    private final EmailVerificationMapper emailVerificationMapper;

    @Transactional
    @Override
    public Optional<EmailVerification> findActiveOtp(UUID userId, OtpType verifyType) {
        return jpaEmailVerification
                .findByUserIdAndStatusAndAndType(userId, OtpStatus.ACTIVE, verifyType)
                .map(emailVerificationMapper::toDomain);
    }

    @Transactional
    @Override
    public Optional<EmailVerification> findActiveOtpByToken(UUID userId, String token) {
        return jpaEmailVerification
                .findActiveOtpByToken(userId, token)
                .map(emailVerificationMapper::toDomain);
    }

    @Override
    @Transactional
    public void markExpired(UUID id) {
        jpaEmailVerification.markExpired(id);
    }

    @Override
    @Transactional
    public void markVerified(UUID id) {
        jpaEmailVerification.markVerified(id);
    }

    @Override
    public void save(EmailVerification ev) {
        jpaEmailVerification.save(emailVerificationMapper.toEntity(ev));
    }

    @Override
    @Transactional
    public void increaseAttempt(UUID id) {
        jpaEmailVerification.increaseAttempt(id);
    }
}

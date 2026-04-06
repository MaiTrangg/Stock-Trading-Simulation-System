package com.trading.demo.auth.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.infrastructure.persistence.entity.EmailVerificationEntity;

@Mapper(componentModel = "Spring")
public interface EmailVerificationMapper {
    EmailVerificationEntity toEntity(EmailVerification domain);

    EmailVerification toDomain(EmailVerificationEntity entity);
}

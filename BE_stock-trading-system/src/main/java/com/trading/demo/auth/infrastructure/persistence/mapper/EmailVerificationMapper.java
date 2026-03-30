package com.trading.demo.auth.infrastructure.persistence.mapper;

import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.infrastructure.persistence.entity.EmailVerificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface EmailVerificationMapper {
    EmailVerificationEntity toEntity(EmailVerification domain);
    EmailVerification toDomain(EmailVerificationEntity entity);
}

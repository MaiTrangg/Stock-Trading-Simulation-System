package com.trading.stock_trading_system.auth.infrastructure.persistence.mapper;

import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.infrastructure.persistence.entity.EmailVerificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface EmailVerificationMapper {
    EmailVerificationEntity toEntity(EmailVerification domain);
    EmailVerification toDomain(EmailVerificationEntity entity);
}

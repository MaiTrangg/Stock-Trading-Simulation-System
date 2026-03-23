package com.trading.stock_trading_system.auth.infrastructure.persistence.mapper;

import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    RefreshToken toDomain(RefreshTokenEntity entity);

    RefreshTokenEntity toEntity(RefreshToken domain);
}

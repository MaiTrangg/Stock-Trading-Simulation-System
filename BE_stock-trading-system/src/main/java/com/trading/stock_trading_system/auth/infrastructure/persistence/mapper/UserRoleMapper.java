package com.trading.stock_trading_system.auth.infrastructure.persistence.mapper;

import com.trading.stock_trading_system.auth.domain.model.UserRole;
import com.trading.stock_trading_system.auth.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleEntity toEntity(UserRole domain);
}

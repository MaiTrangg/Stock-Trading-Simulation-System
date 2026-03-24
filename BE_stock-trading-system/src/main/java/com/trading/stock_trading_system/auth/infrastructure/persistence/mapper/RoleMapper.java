package com.trading.stock_trading_system.auth.infrastructure.persistence.mapper;

import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.awt.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleEntity roleEntity);
    RoleEntity toEntity(Role domain);
}

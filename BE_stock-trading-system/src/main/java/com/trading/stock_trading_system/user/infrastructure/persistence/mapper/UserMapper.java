package com.trading.stock_trading_system.user.infrastructure.persistence.mapper;


import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);
}

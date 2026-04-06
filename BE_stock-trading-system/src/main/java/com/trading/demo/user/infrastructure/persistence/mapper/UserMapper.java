package com.trading.demo.user.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.auth.application.dto.response.RegisterResponse;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.infrastructure.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

    RegisterResponse toRegisterResponse(User user);
}

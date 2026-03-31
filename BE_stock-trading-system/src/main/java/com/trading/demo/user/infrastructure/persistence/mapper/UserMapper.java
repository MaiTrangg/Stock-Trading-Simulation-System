package com.trading.demo.user.infrastructure.persistence.mapper;


import com.trading.demo.auth.application.dto.RegisterResponse;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

    RegisterResponse toRegisterResponse(User user);
}

package com.trading.demo.auth.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.auth.domain.model.UserRole;
import com.trading.demo.auth.infrastructure.persistence.entity.UserRoleEntity;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleEntity toEntity(UserRole domain);
}

package com.trading.demo.auth.infrastructure.persistence.mapper;

import com.trading.demo.auth.domain.model.UserRole;
import com.trading.demo.auth.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleEntity toEntity(UserRole domain);
}

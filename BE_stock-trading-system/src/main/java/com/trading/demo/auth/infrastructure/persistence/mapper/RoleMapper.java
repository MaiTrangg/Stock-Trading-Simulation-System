package com.trading.demo.auth.infrastructure.persistence.mapper;

import com.trading.demo.auth.domain.model.Role;
import com.trading.demo.auth.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleEntity roleEntity);
    RoleEntity toEntity(Role domain);
}

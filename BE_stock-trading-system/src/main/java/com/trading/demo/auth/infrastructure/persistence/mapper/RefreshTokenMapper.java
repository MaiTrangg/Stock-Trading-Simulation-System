package com.trading.demo.auth.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.infrastructure.persistence.entity.RefreshTokenEntity;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    RefreshToken toDomain(RefreshTokenEntity entity);

    RefreshTokenEntity toEntity(RefreshToken domain);
}

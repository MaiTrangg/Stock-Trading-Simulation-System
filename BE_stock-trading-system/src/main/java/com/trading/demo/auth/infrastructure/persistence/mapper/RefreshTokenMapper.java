package com.trading.demo.auth.infrastructure.persistence.mapper;

import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    RefreshToken toDomain(RefreshTokenEntity entity);

    RefreshTokenEntity toEntity(RefreshToken domain);
}

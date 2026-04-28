package com.trading.demo.portfolio.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.infrastructure.persistence.entity.FundEntity;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    // Map FundEntity sang Wallet Model
    Fund toDomain(FundEntity entity);

    FundEntity toEntity(Fund domain);
}

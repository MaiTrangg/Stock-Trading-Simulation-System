package com.trading.demo.portfolio.infrastructure.persistence.mapper;

import com.trading.demo.common.mapper.EnumMapper;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.trading.demo.portfolio.domain.model.WalletTransaction;
import com.trading.demo.portfolio.infrastructure.persistence.entity.WalletTransactionEntity;

@Mapper(componentModel = "spring")
public interface WalletTransactionMapper {

    WalletTransaction toDomain(WalletTransactionEntity entity);

    WalletTransactionEntity toEntity(WalletTransaction domain);

}

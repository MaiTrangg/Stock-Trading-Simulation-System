package com.trading.demo.trading.infrastructure.persistence.mapper;

import com.trading.demo.common.mapper.EnumMapper;
import org.mapstruct.Mapper;

import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.infrastructure.persistence.entity.TradeEntity;

@Mapper(componentModel = "spring", uses = EnumMapper.class)
public interface TradeMapper {
    Trade toDomain(TradeEntity entity);

    TradeEntity toEntity(Trade domain);
}

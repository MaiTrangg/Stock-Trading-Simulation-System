package com.trading.demo.marketdata.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.trading.demo.marketdata.domain.model.StockPriceHistory;
import com.trading.demo.marketdata.infrastructure.persistence.entity.StockPriceHistoryEntity;

@Mapper(componentModel = "spring")
public interface StockPriceHistoryMapper {
    StockPriceHistory toDomain(StockPriceHistoryEntity entity);

    StockPriceHistoryEntity toEntity(StockPriceHistory domain);

    List<StockPriceHistoryEntity> toEntityList(List<StockPriceHistory> list);
}

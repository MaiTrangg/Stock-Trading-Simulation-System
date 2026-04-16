package com.trading.demo.stock.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.trading.demo.stock.domain.model.Stock;
import com.trading.demo.stock.infrastructure.persistence.entity.StockEntity;


@Mapper(componentModel = "Spring")
public interface StockMapper {
    Stock toDomain(StockEntity entity);

    List<StockEntity> toEntityList(List<Stock> list);

    StockEntity toEntity(Stock domain);

}

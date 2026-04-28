package com.trading.demo.portfolio.infrastructure.persistence.mapper;

import com.trading.demo.portfolio.domain.model.PortfolioTransaction;
import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioTransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioTransactionMapper {

    PortfolioTransaction toDomain(PortfolioTransactionEntity entity);

    PortfolioTransactionEntity toEntity(PortfolioTransaction domain);
}

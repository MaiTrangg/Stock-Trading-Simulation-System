package com.trading.demo.portfolio.infrastructure.persistence.mapper;

import com.trading.demo.portfolio.domain.model.Portfolio;
import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    Portfolio toDomain(PortfolioEntity entity);

    PortfolioEntity toEntity(Portfolio domain);
}

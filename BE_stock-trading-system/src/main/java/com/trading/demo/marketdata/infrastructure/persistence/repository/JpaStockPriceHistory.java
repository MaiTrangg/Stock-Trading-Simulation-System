package com.trading.demo.marketdata.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.demo.marketdata.infrastructure.persistence.entity.StockPriceHistoryEntity;

public interface JpaStockPriceHistory extends JpaRepository<StockPriceHistoryEntity, UUID> {
    List<StockPriceHistoryEntity> findByStockId(UUID stockId);

    boolean existsByStockIdAndPriceDate(UUID id, LocalDate priceDate);
}

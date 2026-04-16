package com.trading.demo.marketdata.infrastructure.persistence.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.trading.demo.marketdata.domain.model.StockPriceHistory;
import com.trading.demo.marketdata.domain.port.out.StockPriceHistoryRepositoryPort;
import com.trading.demo.marketdata.infrastructure.persistence.mapper.StockPriceHistoryMapper;
import com.trading.demo.marketdata.infrastructure.persistence.repository.JpaStockPriceHistory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockPriceHistoryRepoImpl implements StockPriceHistoryRepositoryPort {

    private final JpaStockPriceHistory jpa;
    private final StockPriceHistoryMapper mapper;

    @Override
    public List<StockPriceHistory> findByStockId(UUID stockId) {
        return jpa.findByStockId(stockId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<StockPriceHistory> data) {
        jpa.saveAll(mapper.toEntityList(data));
    }

    @Override
    public boolean existsByStockIdAndPriceDate(UUID stockId, LocalDate priceDate) {
        return jpa.existsByStockIdAndPriceDate(stockId, priceDate);
    }

    @Override
    public void save(StockPriceHistory stockPriceHistory) {
        jpa.save(mapper.toEntity(stockPriceHistory));
    }
}

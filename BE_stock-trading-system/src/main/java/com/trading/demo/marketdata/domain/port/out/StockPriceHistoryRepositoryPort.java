package com.trading.demo.marketdata.domain.port.out;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.trading.demo.marketdata.domain.model.StockPriceHistory;

public interface StockPriceHistoryRepositoryPort {
    List<StockPriceHistory> findByStockId(UUID stockId);

    void saveAll(List<StockPriceHistory> data);

    boolean existsByStockIdAndPriceDate(UUID stockId, LocalDate date);

    void save(StockPriceHistory stockPriceHistory);
}

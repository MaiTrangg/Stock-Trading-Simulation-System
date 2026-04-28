package com.trading.demo.marketdata.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.model.StockPriceHistory;
import com.trading.demo.marketdata.domain.port.out.StockPriceHistoryRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class StockPriceHistoryService {

    private final StockPriceHistoryRepositoryPort priceHistoryRepo;

    @Async //  phuong thuc nay chay tren ThreadPool rieng, khong lam gian doan luong chinh
    @Transactional
    public void saveHistoryAsync(UUID stockId, MarketPrice price) {
        LocalDate today = price.getTimestamp().toLocalDate();

        // kiem tra tranh duplicate record cho cung mot ngay (Audit)
        if (!priceHistoryRepo.existsByStockIdAndPriceDate(stockId, today)) {
            StockPriceHistory history = StockPriceHistory.fromMarketPrice(stockId, price);
            priceHistoryRepo.save(history);
            log.debug("Async History Saved for: {}", price.getSymbol());
        }
    }
}

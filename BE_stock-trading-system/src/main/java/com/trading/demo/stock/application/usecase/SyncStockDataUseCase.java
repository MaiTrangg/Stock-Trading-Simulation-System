package com.trading.demo.stock.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trading.demo.stock.domain.model.Stock;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.stock.infrastructure.external.StockListProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncStockDataUseCase {

    private final StockRepository repository;
    private final StockListProvider provider;

    public void syncDaily() {

        log.info("START SYNC STOCK");

        List<Stock> stocks = provider.fetchAll();
        repository.saveAll(stocks);

        log.info("SYNC DONE: {}", stocks.size());
    }
}

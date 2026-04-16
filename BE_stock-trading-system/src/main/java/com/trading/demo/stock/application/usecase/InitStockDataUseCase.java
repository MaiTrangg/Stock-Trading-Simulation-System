package com.trading.demo.stock.application.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.trading.demo.stock.domain.model.Stock;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.stock.infrastructure.external.StockListProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitStockDataUseCase {

    private final StockRepository repository;
    private final StockListProvider provider;

    public void initIfEmpty() {

        long count = repository.count();

        if (count == 0) {
            log.info("INIT STOCK DATA");

            List<Stock> stocks = provider.fetchAll();

            // FIX: remove duplicate theo symbol
            Map<String, Stock> uniqueMap = new HashMap<>();

            for (Stock stock : stocks) {
                uniqueMap.put(stock.getSymbol(), stock);
            }

            List<Stock> uniqueStocks = new ArrayList<>(uniqueMap.values());

            repository.saveAll(uniqueStocks);

            log.info("INIT DONE: {}", stocks.size());

        } else {
            log.info("SKIP INIT, DB NOT EMPTY");
        }
    }
}

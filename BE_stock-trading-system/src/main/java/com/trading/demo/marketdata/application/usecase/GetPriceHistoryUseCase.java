package com.trading.demo.marketdata.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.marketdata.domain.model.StockPriceHistory;
import com.trading.demo.marketdata.domain.port.out.StockPriceHistoryRepositoryPort;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPriceHistoryUseCase {

    private final StockPriceHistoryRepositoryPort repository;
    private final StockRepository stockRepository;

    public List<StockPriceHistory> get(String symbol) {

        UUID stockId = stockRepository
                .findIdBySymbol(symbol);
        return repository.findByStockId(stockId);
    }
}

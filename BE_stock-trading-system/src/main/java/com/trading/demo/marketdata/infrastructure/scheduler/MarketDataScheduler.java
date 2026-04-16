package com.trading.demo.marketdata.infrastructure.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trading.demo.marketdata.application.usecase.FetchBatchMarketPriceUseCase;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarketDataScheduler {

    private final FetchBatchMarketPriceUseCase fetchUseCase;
    private final StockRepository stockRepository;

    @Scheduled(fixedRate = 5000)
    public void fetch() {

        List<String> symbols = stockRepository.findAllActiveSymbols();

        if (symbols.isEmpty()) {
            log.warn("NO SYMBOLS");
            return;
        }
        fetchUseCase.fetch(symbols);
    }
}

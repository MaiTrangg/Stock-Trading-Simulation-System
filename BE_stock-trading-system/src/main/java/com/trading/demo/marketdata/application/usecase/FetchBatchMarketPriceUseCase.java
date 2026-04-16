package com.trading.demo.marketdata.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.MarketDataProviderPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class FetchBatchMarketPriceUseCase {

    private final MarketDataProviderPort provider;
    private final HandleRealtimePriceUseCase handler;

    public void fetch(List<String> symbols) {
        try {
            List<MarketPrice> prices = provider.getLatestPrices(symbols);

            if (prices == null || prices.isEmpty()) {
                log.warn("NO PRICE DATA: {}", symbols);
                return;
            }

            for (MarketPrice price : prices) {
                handler.handle(price);
            }

        } catch (Exception e) {
            log.error("FETCH BATCH FAILED: {}", symbols, e);
        }
    }
}

package com.trading.demo.marketdata.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trading.demo.marketdata.infrastructure.cache.InMemoryPriceCache;
import com.trading.demo.marketdata.infrastructure.messaging.SpringWebSocketPublisher;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CachePushService {

    private final StockRepository stockRepository;
    private final InMemoryPriceCache cache;
    private final SpringWebSocketPublisher publisher;

    public int pushAllFromCache() {
        List<String> symbols = stockRepository.findAllActiveSymbols();

        int count = 0;

        for (String symbol : symbols) {
            var price = cache.get(symbol);

            if (price != null) {
                publisher.push(price);
                count++;
            }
        }

        return count;
    }

    public boolean isCacheEmpty() {
        List<String> symbols = stockRepository.findAllActiveSymbols();

        for (String symbol : symbols) {
            if (cache.get(symbol) != null) {
                return false;
            }
        }

        return true;
    }
}

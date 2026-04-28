package com.trading.demo.marketdata.infrastructure.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.PriceCachePort;


@Component
public class InMemoryPriceCache implements PriceCachePort {
    private static class CacheItem {
        MarketPrice price;
        long timestamp;
    }

    private static final long TTL = 7000;

    private final Map<String, CacheItem> cache = new ConcurrentHashMap<>();

    @Override
    public void put(MarketPrice price) {
        CacheItem item = new CacheItem();
        item.price = price;
        item.timestamp = System.currentTimeMillis();

        cache.put(price.getSymbol(), item);
    }

    @Override
    public MarketPrice get(String symbol) {
        CacheItem item = cache.get(symbol);

        if (item == null) {
            return null;
        }

        // expire cache
        if (System.currentTimeMillis() - item.timestamp > TTL) {
            cache.remove(symbol);
            return null;
        }

        return item.price;
    }
}

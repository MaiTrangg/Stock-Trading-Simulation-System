package com.trading.demo.marketdata.domain.port.out;

import com.trading.demo.marketdata.domain.model.MarketPrice;

public interface PriceCachePort {
    void put(MarketPrice price);

    MarketPrice get(String symbol);
}

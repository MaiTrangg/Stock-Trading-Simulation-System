package com.trading.demo.marketdata.domain.port.out;

import com.trading.demo.marketdata.domain.model.MarketPrice;

public interface PricePushPort {
    void push(MarketPrice price);
}

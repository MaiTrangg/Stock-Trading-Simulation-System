package com.trading.demo.marketdata.domain.port.out;

import java.util.List;

import com.trading.demo.marketdata.domain.model.MarketPrice;

public interface MarketDataProviderPort {

    List<MarketPrice> getLatestPrices(List<String> symbols);

}

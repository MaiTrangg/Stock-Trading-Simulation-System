package com.trading.demo.marketdata.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketPrice {
    private String symbol;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume;
    private LocalDateTime timestamp;
    private BigDecimal ceilingPrice;
    private BigDecimal floorPrice;

    private BigDecimal bestBidPrice;// price1 of bid
    private BigDecimal bestAskPrice; // price1 of ask

    private int bestBidQuantity; // quantity1 of bid
    private int bestAskQuantity; // quantity1 of ask
}

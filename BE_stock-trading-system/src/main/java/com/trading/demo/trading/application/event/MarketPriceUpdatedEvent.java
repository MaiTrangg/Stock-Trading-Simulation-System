package com.trading.demo.trading.application.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

/**
 * Event: duoc publish khi gia thi truong thay doi
 */
@Getter
@ToString
public class MarketPriceUpdatedEvent implements Serializable {

    private final String symbol;
    private final UUID eventId;

    private final BigDecimal bestBidPrice;
    private final BigDecimal bestAskPrice;

    private final int bestBidQuantity;
    private final int bestAskQuantity;

    private final LocalDateTime updatedAt;

    public MarketPriceUpdatedEvent(
            String symbol,
            BigDecimal bestBidPrice,
            BigDecimal bestAskPrice,
            int bestBidQuantity,
            int bestAskQuantity
    ) {
        this.eventId = UUID.randomUUID();
        this.symbol = symbol;
        this.bestBidPrice = bestBidPrice;
        this.bestAskPrice = bestAskPrice;
        this.bestBidQuantity = bestBidQuantity;
        this.bestAskQuantity = bestAskQuantity;
        this.updatedAt = LocalDateTime.now();
    }
}

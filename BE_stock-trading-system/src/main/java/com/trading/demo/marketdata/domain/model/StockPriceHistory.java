package com.trading.demo.marketdata.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockPriceHistory {

    private UUID id;
    private UUID stockId;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Long volume;
    private LocalDate priceDate;

    public static StockPriceHistory create(
            UUID id,
            UUID stockId,
            BigDecimal openPrice,
            BigDecimal closePrice,
            BigDecimal highPrice,
            BigDecimal lowPrice,
            Long volume,
            LocalDate priceDate
    ) {
        return StockPriceHistory.builder()
                .id(id)
                .stockId(stockId)
                .openPrice(openPrice)
                .closePrice(closePrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .volume(volume)
                .priceDate(priceDate)
                .build();
    }

    public static StockPriceHistory fromMarketPrice(UUID stockId, MarketPrice price) {
        StockPriceHistory sph = new StockPriceHistory();

        sph.setId(UUID.randomUUID()); // generate id
        sph.setStockId(stockId);

        // mapping OHLC
        sph.setOpenPrice(price.getOpen());
        sph.setClosePrice(price.getClose());
        sph.setHighPrice(price.getHigh());
        sph.setLowPrice(price.getLow());

        sph.setVolume(price.getVolume());

        //get date
        sph.setPriceDate(price.getTimestamp().toLocalDate());
        return sph;
    }
}


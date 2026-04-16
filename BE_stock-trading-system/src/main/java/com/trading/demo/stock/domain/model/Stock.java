package com.trading.demo.stock.domain.model;

import java.util.UUID;

import com.trading.demo.stock.domain.enums.StockStatus;

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
public class Stock {
    private UUID id;
    private String symbol;
    private String companyName;
    private String exchange;
    private String sector;
    private String industry;

    private String status;
    private boolean deleted;

    public static Stock create(String symbol, String companyName, String exchange) {
        return Stock.builder()
                .id(UUID.randomUUID())
                .symbol(symbol)
                .companyName(companyName)
                .exchange(exchange)
                .sector(null)
                .industry(null)
                .status(StockStatus.ACTIVE.name())
                .deleted(false)
                .build();
    }
}

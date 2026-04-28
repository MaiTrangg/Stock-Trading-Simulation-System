package com.trading.demo.marketdata.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastPriceSnapshot {
    private BigDecimal close;
    private BigDecimal bid;
    private BigDecimal ask;
}

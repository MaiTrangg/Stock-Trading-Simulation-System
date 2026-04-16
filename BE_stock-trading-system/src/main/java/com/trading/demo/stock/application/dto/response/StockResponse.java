package com.trading.demo.stock.application.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private UUID id;
    private String symbol;
    private String companyName;
    private String exchange;
}

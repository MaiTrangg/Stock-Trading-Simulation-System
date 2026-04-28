package com.trading.demo.trading.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private UUID stockId;
    private String side;
    private String type;
    private BigDecimal price;
    private int quantity;
}

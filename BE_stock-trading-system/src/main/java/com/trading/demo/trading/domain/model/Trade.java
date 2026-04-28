package com.trading.demo.trading.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Trade {
    private final UUID id;
    private final UUID orderId;
    private final UUID buyerId;
    private final UUID sellerId;
    private final UUID stockId;
    private final String side;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal totalAmount;
    private final LocalDateTime executedAt;
}

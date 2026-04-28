package com.trading.demo.trading.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private UUID id;
    private UUID orderId;
    private OrderStatus oldStatus;
    private OrderStatus newStatus;
    private UUID changedBy;
    private LocalDateTime createdAt;
}

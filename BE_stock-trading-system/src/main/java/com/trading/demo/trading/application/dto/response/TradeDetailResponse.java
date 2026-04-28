package com.trading.demo.trading.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDetailResponse {
    private UUID id;
    private UUID orderId;
    private UUID stockId;
    private String side;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private LocalDateTime executedAt;
    private UUID buyerId;
    private UUID sellerId;
}

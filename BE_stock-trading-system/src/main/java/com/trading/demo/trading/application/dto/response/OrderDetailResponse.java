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
public class OrderDetailResponse {
    private UUID id;
    private String orderNo;
    private UUID stockId;
    private String side;
    private String type;
    private BigDecimal price;
    private Integer quantity;
    private Integer executedQuantity;
    private Integer remainingQuantity;
    private String status;
    private LocalDateTime createdAt;
}

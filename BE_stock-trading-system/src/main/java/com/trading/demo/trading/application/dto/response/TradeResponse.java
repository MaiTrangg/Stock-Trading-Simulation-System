package com.trading.demo.trading.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeResponse {
    private UUID id;
    private UUID stockId;
    private OrderSide side;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime executedAt;
}

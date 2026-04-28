package com.trading.demo.portfolio.presentation.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldingResponse {
    private UUID stockId;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer lockedQuantity;
    private BigDecimal avgPrice;
}

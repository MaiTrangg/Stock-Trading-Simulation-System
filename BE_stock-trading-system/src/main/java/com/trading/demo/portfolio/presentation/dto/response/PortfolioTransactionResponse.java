package com.trading.demo.portfolio.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioTransactionResponse {

    private UUID id;

    private UUID stockId;

    private Integer quantity;

    private String type; // BUY / SELL / RELEASE / LOCK

    private UUID referenceId;

    private LocalDateTime createdAt;
}

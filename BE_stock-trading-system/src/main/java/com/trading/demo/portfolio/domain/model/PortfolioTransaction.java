package com.trading.demo.portfolio.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioTransaction {

    private UUID id;

    private UUID userId;
    private UUID stockId;

    private Integer quantity;

    private PortfolioTransactionType transactionType;

    private UUID referenceId;

    private LocalDateTime createdAt;
}

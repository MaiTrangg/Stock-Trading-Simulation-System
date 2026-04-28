package com.trading.demo.portfolio.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioTransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionResponse {
    private UUID id;
    private UUID userId;
    private WalletTransactionType type;// DEPOSIT / WITHDRAW / RESERVE / REFUND
    private BigDecimal amount;
    private UUID referenceId;
    private LocalDateTime createdAt;
}

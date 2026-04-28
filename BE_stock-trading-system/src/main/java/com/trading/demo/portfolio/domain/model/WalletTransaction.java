package com.trading.demo.portfolio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WalletTransaction {
    private final UUID id;
    private final UUID userId;
    private final WalletTransactionType type;
    private final BigDecimal amount;
    private final UUID referenceId;
    private final LocalDateTime createdAt;
}

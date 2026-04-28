package com.trading.demo.portfolio.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.model.WalletTransaction;


public interface WalletTransactionPort {
    void logTransaction(UUID userId, WalletTransactionType type, BigDecimal amount, UUID referenceId);

    boolean existsByReferenceId(UUID referenceId);

    List<WalletTransaction> findTransactions(
            UUID userId,
            WalletTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    );
}

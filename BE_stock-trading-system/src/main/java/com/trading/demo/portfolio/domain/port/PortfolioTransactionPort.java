package com.trading.demo.portfolio.domain.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import com.trading.demo.portfolio.domain.model.PortfolioTransaction;


public interface PortfolioTransactionPort {

    List<PortfolioTransaction> findTransactions(
            UUID userId,
            UUID stockId,
            PortfolioTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    );
}

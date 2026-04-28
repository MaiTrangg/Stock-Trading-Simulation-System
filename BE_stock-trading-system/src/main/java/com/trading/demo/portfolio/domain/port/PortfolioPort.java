package com.trading.demo.portfolio.domain.port;

import com.trading.demo.portfolio.domain.model.Portfolio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PortfolioPort {
    void lockStock(UUID userId, UUID stockId, UUID orderId, int quantity);

    void releaseStock(UUID userId, UUID stockId, UUID orderId, int quantity);

    void updateAfterTrade(UUID userId,
                          UUID stockId,
                          UUID orderId,
                          int quantityChange,
                          BigDecimal tradePrice);

    Optional<Portfolio> findByUserIdAndStockId(UUID userId, UUID stockId);

    Portfolio save(Portfolio portfolio);

    List<Portfolio> findByUserId(UUID userId);

}

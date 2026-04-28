package com.trading.demo.trading.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.model.Trade;

public interface TradePort {
    List<Trade> findTrades(
            UUID userId,
            UUID stockId,
            OrderSide side,
            LocalDateTime from,
            LocalDateTime to
    );

    /**
     * Luu vet giao dich khop lenh thanh cong
     */
    void saveTrade(Trade trade);

    /**
     * Check if trade already exists
     */
    boolean existsByOrderIdAndPriceAndQuantity(UUID orderId, BigDecimal price, int quantity);

    /**
     * Find trades by user ID
     */
    List<Trade> findByUserId(UUID userId);

    /**
     * Find trade by ID
     */
    Trade findById(UUID id);

    List<Trade> findByOrderId(UUID orderId);
}

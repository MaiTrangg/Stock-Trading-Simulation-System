package com.trading.demo.trading.domain.repository;

import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.OrderHistory;

public interface OrderHistoryRepository {
    void save(UUID orderId,
              OrderStatus oldStatus,
              OrderStatus newStatus,
              UUID changedBy);
    
    List<OrderHistory> findByOrderId(UUID orderId);
}

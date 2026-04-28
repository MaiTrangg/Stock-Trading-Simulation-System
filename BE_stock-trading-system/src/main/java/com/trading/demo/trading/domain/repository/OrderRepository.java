package com.trading.demo.trading.domain.repository;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void save(Order order);

    Order findById(UUID id);

    List<Order> findBySymbolAndStatusIn(String symbol, List<OrderStatus> statuses);

    /**
     * Atomic order matching with optimistic locking
     *
     * @param orderId  Order ID
     * @param matchQty Quantity to match
     * @param version  Current version for optimistic locking
     * @return Number of rows updated (0 if version mismatch)
     */
//    int executeMatchAtomic(UUID orderId, int matchQty, Long version);

    /**
     * Atomic order cancellation with optimistic locking
     *
     * @param orderId Order ID
     * @param version Current version for optimistic locking
     * @return Number of rows updated (0 if version mismatch)
     */
    int executeCancelAtomic(UUID orderId, Long version,
                            OrderStatus canceledStstus,
                            List<OrderStatus> allowedStatuses);

    /**
     * Find orders by user ID with pagination and optional status filter
     */
//    List<Order> findByUserId(UUID userId);

    List<Order> search(UUID userId,
                       OrderStatus status,
                       UUID stockId,
                       OrderSide side,
                       LocalDateTime from,
                       LocalDateTime to);

//    List<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);

    List<Order> findOpenOrders(UUID userId, UUID stockId, List<OrderStatus> openStatuses);
}

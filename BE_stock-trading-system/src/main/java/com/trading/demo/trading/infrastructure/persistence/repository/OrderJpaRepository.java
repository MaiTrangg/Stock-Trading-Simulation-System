package com.trading.demo.trading.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.infrastructure.persistence.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("""
            SELECT o FROM OrderEntity o
            JOIN StockEntity s
            WHERE s.symbol = :symbol
            AND o.status IN :openStatuses
            """)
    List<OrderEntity> findBySymbolAndStatusIn(String symbol, List<OrderStatus> openStatuses);

//    @Modifying
//    @Query("UPDATE OrderEntity o SET o.executedQuantity = o.executedQuantity + :matchQty, " +
//            "o.remainingQuantity = o.remainingQuantity - :matchQty, " +
//            "o.status = CASE WHEN o.remainingQuantity - :matchQty = 0 THEN 'FILLED' ELSE 'PARTIALLY_FILLED' END, " +
//            "o.version = o.version + 1 " +
//            "WHERE o.id = :orderId AND o.remainingQuantity >= :matchQty AND o.version = :version")
//    int executeMatchAtomic(@Param("orderId") UUID orderId,
//                           @Param("matchQty") int matchQty,
//                           @Param("version") Long version);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE OrderEntity o
                SET o.status = :cancelledStatus,
                    o.remainingQuantity = 0,
                    o.version = o.version + 1
                WHERE o.id = :orderId
                AND o.status IN :allowedStatuses
                AND o.version = :version
            """)
    int executeCancelAtomic(
            @Param("orderId") UUID orderId,
            @Param("version") Long version,
            @Param("cancelledStatus") OrderStatus cancelledStatus,
            @Param("allowedStatuses") List<OrderStatus> allowedStatuses
    );

//    List<OrderEntity> findByUserId(UUID userId);

//    List<OrderEntity> findByUserIdAndStatus(UUID userId, OrderStatus status);

    @Query("""
            SELECT o FROM OrderEntity o
                WHERE o.userId = :userId
                AND (:status IS NULL OR o.status = :status)
                AND (:stockId IS NULL OR o.stockId = :stockId)
                AND (:side IS NULL OR o.side = :side)
                AND (o.createdAt >= COALESCE(:from, o.createdAt))
                AND (o.createdAt <= COALESCE(:to, o.createdAt))
            """)
    List<OrderEntity> search(
            UUID userId,
            OrderStatus status,
            UUID stockId,
            OrderSide side,
            LocalDateTime from,
            LocalDateTime to
    );

    @Query("""
                SELECT o FROM OrderEntity o
                WHERE o.userId = :userId
                AND o.stockId = :stockId
                AND o.status IN :statuses
            """)
    List<OrderEntity> findOpenOrders(
            UUID userId,
            UUID stockId,
            List<OrderStatus> statuses
    );
}

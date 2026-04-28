package com.trading.demo.trading.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trading.demo.trading.infrastructure.persistence.entity.TradeEntity;

@Repository
public interface TradeJpaRepository extends JpaRepository<TradeEntity, UUID> {
    boolean existsByOrderIdAndPriceAndQuantity(UUID orderId, BigDecimal price, int quantity);

    @Query("SELECT t FROM TradeEntity t WHERE t.buyerId = :userId OR t.sellerId = :userId ORDER BY t.executedAt DESC")
    List<TradeEntity> findByBuyerIdOrSellerId(@Param("userId") UUID userId);

    @Query("""
                SELECT t FROM TradeEntity t
                WHERE t.orderId = :orderId
            """)
    List<TradeEntity> findByOrderId(UUID orderId);
}

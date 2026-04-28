package com.trading.demo.trading.infrastructure.persistence.entity;

import com.trading.demo.trading.domain.enums.OrderSide;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeEntity {
    @Id
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "stock_id")
    private UUID stockId;

    @Enumerated(EnumType.STRING)
    @Column(name = "side")
    private OrderSide side;
    
    private BigDecimal price;
    private int quantity;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @Column(name = "buyer_id")
    private UUID buyerId;

    @Column(name = "seller_id")
    private UUID sellerId;
}

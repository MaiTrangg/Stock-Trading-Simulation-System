package com.trading.demo.trading.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "stock_id")
    private UUID stockId;

    @Enumerated(EnumType.STRING)
    @Column(name = "side")
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    private BigDecimal price;
    private int quantity;

    @Column(name = "executed_quantity")
    private int executedQuantity;

    @Column(name = "remaining_quantity")
    private int remainingQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_delete")
    private boolean isDelete;

    private long version;
}

package com.trading.demo.trading.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.enums.OrderType;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

/**
 * 
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Version
    private Long version;

    private UUID id;
    private String orderNo;

    private UUID userId;
    private UUID stockId;

    private OrderSide side;
    private OrderType type;

    private BigDecimal price;
    private int quantity;

    private int executedQuantity;
    private int remainingQuantity;

    private OrderStatus status;

    private LocalDateTime createdAt;

    public static Order create(
            UUID userId,
            UUID stockId,
            OrderSide side,
            OrderType type,
            BigDecimal price,
            int quantity
    ) {
        return Order.builder()
                .id(UUID.randomUUID())
                .orderNo("ORD-" + System.currentTimeMillis())
                .userId(userId)
                .stockId(stockId)
                .side(side)
                .type(type)
                .price(price)
                .quantity(quantity)
                .executedQuantity(0)
                .remainingQuantity(quantity)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void cancel() {
        if (status == OrderStatus.FILLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_FILLED);
        }
        if (status == OrderStatus.CANCELLED) {
            // Idempotent - already cancelled, no action needed
            return;
        }
        this.status = OrderStatus.CANCELLED;
        this.remainingQuantity = 0;
    }

    public BigDecimal totalAmount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public BigDecimal getRemainingAmount() {
        return price.multiply(BigDecimal.valueOf(remainingQuantity));
    }

    public void executeMatch(int matchQty) {
        if (matchQty <= 0) {
            return;
        }
        //coi lai boi vi lai min(actualmatchqty, remainingQty) truoc do roi ma
        if (matchQty > remainingQuantity) {
            matchQty = remainingQuantity; // khong khop qua so luong dang treo
        }

        this.executedQuantity += matchQty;
        this.remainingQuantity -= matchQty;

        if (this.remainingQuantity == 0) {
            this.status = OrderStatus.FILLED;
        } else {
            this.status = OrderStatus.PARTIALLY_FILLED;
        }
    }
}
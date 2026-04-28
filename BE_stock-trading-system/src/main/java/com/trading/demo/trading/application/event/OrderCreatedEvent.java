package com.trading.demo.trading.application.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

/**
 * Application Event: duoc publish sau khi Order duoc tao thanh cong (AFTER_COMMIT)
 */
@Getter
@ToString
public class OrderCreatedEvent implements Serializable {

    private final UUID eventId;      // Idempotency key
    private final UUID orderId;
    private final UUID stockId;
    private final LocalDateTime createdAt;

    public OrderCreatedEvent(UUID orderId, UUID stockId) {
        this.eventId = UUID.randomUUID();  // Unique per event
        this.orderId = orderId;
        this.stockId = stockId;
        this.createdAt = LocalDateTime.now();
    }
}

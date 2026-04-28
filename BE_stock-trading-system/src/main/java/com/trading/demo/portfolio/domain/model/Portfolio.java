package com.trading.demo.portfolio.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class Portfolio {
    private final UUID id;
    private final UUID userId;
    private final UUID stockId;
    private int quantity;        // tong so luong
    private int lockedQuantity;  // dang treo ban
    private BigDecimal avgPrice;

    // so luong thuc te co the dung de ban
    public int getAvailableQuantity() {
        return this.quantity - this.lockedQuantity;
    }

    public boolean canSell(int qty) {
        return getAvailableQuantity() >= qty;
    }

    public static Portfolio createEmpty(UUID userId, UUID stockId) {
        return Portfolio.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .stockId(stockId)
                .quantity(0)
                .lockedQuantity(0)
                .avgPrice(BigDecimal.ZERO)
                .build();
    }
}

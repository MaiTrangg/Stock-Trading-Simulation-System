package com.trading.demo.portfolio.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Fund {
    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;           // Tong tien
    private BigDecimal availableBalance;  // Tien co the dat lenh
    private BigDecimal reservedBalance;   // Tien dang khoa

    public static Fund createEmpty(UUID userId) {
        return Fund.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .balance(BigDecimal.valueOf(100_000))
                .availableBalance(BigDecimal.valueOf(100_000))
                .reservedBalance(BigDecimal.ZERO)
                .build();
    }
}

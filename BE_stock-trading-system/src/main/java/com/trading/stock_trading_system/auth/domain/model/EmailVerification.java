package com.trading.stock_trading_system.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {

    private UUID id;
    private UUID userId;
    private String token;

    private LocalDateTime expiresAt;
    private boolean verified;
    private int attemptCount;

    private boolean isDelete;
}

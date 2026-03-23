package com.trading.stock_trading_system.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;


    private String token;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    private Boolean revoked;
}

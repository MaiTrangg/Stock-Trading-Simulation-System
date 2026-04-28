package com.trading.demo.portfolio.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "funds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "available_balance", precision = 15, scale = 2)
    private BigDecimal availableBalance;

    @Column(name = "reserved_balance", precision = 15, scale = 2)
    private BigDecimal reservedBalance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Version // Optimistic Locking
    private Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (availableBalance == null) {
            availableBalance = BigDecimal.ZERO;
        }
        if (reservedBalance == null) {
            reservedBalance = BigDecimal.ZERO;
        }
        if (isDelete == null) {
            isDelete = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

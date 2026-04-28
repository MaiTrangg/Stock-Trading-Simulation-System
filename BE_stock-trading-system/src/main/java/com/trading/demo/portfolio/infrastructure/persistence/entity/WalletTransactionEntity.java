package com.trading.demo.portfolio.infrastructure.persistence.entity;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions", uniqueConstraints = @UniqueConstraint(columnNames = "reference_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionEntity {
    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WalletTransactionType type; // RESERVE, TRADE_BUY, TRADE_SELL, REFUND
    private BigDecimal amount;

    @Column(name = "reference_id")
    private UUID referenceId; // order_id hoac trade_id

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.isDelete == null) {
            this.isDelete = false;
        }
    }
}

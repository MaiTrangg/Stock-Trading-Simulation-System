package com.trading.demo.portfolio.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio_transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = "reference_id"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioTransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PortfolioTransactionType transactionType;

    @Column(name = "reference_id", nullable = false, unique = true)
    private UUID referenceId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

//    public enum PortfolioTransactionType {
//
//        LOCK_STOCK(ReferenceIdGenerator.ActionType.LOCK_STOCK),
//        RELEASE_STOCK(ReferenceIdGenerator.ActionType.RELEASE_STOCK),
//        TRADE_BUY(ReferenceIdGenerator.ActionType.TRADE),
//        TRADE_SELL(ReferenceIdGenerator.ActionType.SELL_PROCEED);
//
//        private final ReferenceIdGenerator.ActionType actionType;
//
//        PortfolioTransactionType(ReferenceIdGenerator.ActionType actionType) {
//            this.actionType = actionType;
//        }
//
//        public ReferenceIdGenerator.ActionType getActionType() {
//            return actionType;
//        }
//    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

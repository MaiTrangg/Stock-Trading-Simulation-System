package com.trading.demo.portfolio.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioEntity {
    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "stock_id")
    private UUID stockId;

    @Column(name = "quantity")
    private int quantity; // so luong co the ban

    @Column(name = "locked_quantity")
    private int lockedQuantity;    // so luong dang cho khop ban

    @Column(name = "avg_price")
    private BigDecimal avgPrice;

    @Version
    private Long version;
}

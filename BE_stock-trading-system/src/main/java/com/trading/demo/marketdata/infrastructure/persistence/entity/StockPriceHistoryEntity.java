package com.trading.demo.marketdata.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "stock_price_history")
@Getter
@Setter
public class StockPriceHistoryEntity {
    @Id
    private UUID id;

    @Column(name = "stock_id")
    private UUID stockId;

    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;

    private Long volume;

    private LocalDate priceDate;

    @Column(name = "is_delete")
    private Boolean isDelete;

}

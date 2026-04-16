package com.trading.demo.stock.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@Getter
@Setter
public class StockEntity {

    @Id
    private UUID id;

    private String symbol;

    @Column(name = "company_name")
    private String companyName;

    private String exchange;

    private String sector;

    private String status;

    @Column(name = "is_delete")
    private Boolean deleted = false;
}

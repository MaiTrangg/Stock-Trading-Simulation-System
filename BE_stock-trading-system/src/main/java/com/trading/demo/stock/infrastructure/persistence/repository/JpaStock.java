package com.trading.demo.stock.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trading.demo.stock.infrastructure.persistence.entity.StockEntity;

public interface JpaStock extends JpaRepository<StockEntity, UUID> {
    List<StockEntity> findByStatusAndDeletedFalse(String status);

    boolean existsBySymbol(String symbol);

    @Query("SELECT s.id FROM StockEntity s WHERE s.symbol = :symbol")
    UUID findIdBySymbol(@Param("symbol") String symbol);

}

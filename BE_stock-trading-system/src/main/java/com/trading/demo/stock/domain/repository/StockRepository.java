package com.trading.demo.stock.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trading.demo.stock.domain.model.Stock;
import com.trading.demo.stock.infrastructure.persistence.entity.StockEntity;

public interface StockRepository {
    List<Stock> findAllActive();

    Optional<Stock> findById(UUID id);

    boolean existsBySymbol(String symbol);

    void saveAll(List<Stock> stocks);

    void save(Stock s);

    List<String> findAllActiveSymbols();

    UUID findIdBySymbol(String symbol);

    long count();

    String findSymbolById(UUID id);

}

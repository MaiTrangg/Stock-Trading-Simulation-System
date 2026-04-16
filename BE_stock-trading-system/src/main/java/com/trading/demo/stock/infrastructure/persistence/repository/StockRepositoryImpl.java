package com.trading.demo.stock.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.trading.demo.stock.domain.enums.StockStatus;
import com.trading.demo.stock.domain.model.Stock;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.stock.infrastructure.persistence.mapper.StockMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {
    private final JpaStock jpaStock;
    private final StockMapper stockMapper;

    @Override
    public List<Stock> findAllActive() {
        return jpaStock.findByStatusAndDeletedFalse(StockStatus.ACTIVE.name())
                .stream()
                .map(stockMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Stock> findById(UUID id) {
        return jpaStock.findById(id)
                .map(stockMapper::toDomain);
    }

    @Override
    public boolean existsBySymbol(String symbol) {
        return jpaStock.existsBySymbol(symbol);
    }

    @Override
    public void saveAll(List<Stock> stocks) {
        jpaStock.saveAllAndFlush(stockMapper.toEntityList(stocks));
    }

    @Override
    public void save(Stock s) {
        jpaStock.save(stockMapper.toEntity(s));
    }

    @Override
    public List<String> findAllActiveSymbols() {
        return jpaStock.findByStatusAndDeletedFalse(StockStatus.ACTIVE.name())
                .stream()
                .map(entity -> entity.getSymbol())
                .toList();
    }

    @Override
    public UUID findIdBySymbol(String symbol) {
        return jpaStock.findIdBySymbol(symbol);
    }

    @Override
    public long count() {
        return jpaStock.count();
    }

}

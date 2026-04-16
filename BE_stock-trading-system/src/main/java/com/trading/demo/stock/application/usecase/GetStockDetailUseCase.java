package com.trading.demo.stock.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.stock.application.dto.response.StockResponse;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetStockDetailUseCase {

    private final StockRepository repository;

    public StockResponse execute(UUID id) {
        var s = repository.findById(id)
                .orElseThrow();

        return new StockResponse(
                s.getId(),
                s.getSymbol(),
                s.getCompanyName(),
                s.getExchange()
        );
    }
}

package com.trading.demo.stock.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trading.demo.stock.application.dto.response.StockResponse;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GetStocksUseCase {

    private final StockRepository repository;

    public List<StockResponse> execute() {
        return repository.findAllActive()
                .stream()
                .map(s -> new StockResponse(
                        s.getId(),
                        s.getSymbol(),
                        s.getCompanyName(),
                        s.getExchange()
                ))
                .toList();
    }
}

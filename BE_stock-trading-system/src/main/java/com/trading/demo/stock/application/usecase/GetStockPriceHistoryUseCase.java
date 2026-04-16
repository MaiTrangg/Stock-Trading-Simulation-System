package com.trading.demo.stock.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.marketdata.domain.port.out.StockPriceHistoryRepositoryPort;
import com.trading.demo.stock.application.dto.response.PriceHistoryResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GetStockPriceHistoryUseCase {

    private final StockPriceHistoryRepositoryPort repository;

    public List<PriceHistoryResponse> execute(UUID stockId) {
        return repository.findByStockId(stockId)
                .stream()
                .map(p -> new PriceHistoryResponse(
                        p.getPriceDate(),
                        p.getOpenPrice(),
                        p.getClosePrice(),
                        p.getHighPrice(),
                        p.getLowPrice(),
                        p.getVolume()
                ))
                .toList();
    }
}

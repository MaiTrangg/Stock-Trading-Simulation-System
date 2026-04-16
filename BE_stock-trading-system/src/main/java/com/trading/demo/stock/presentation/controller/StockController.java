package com.trading.demo.stock.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.marketdata.application.constant.MarketDataConstant;
import com.trading.demo.stock.application.dto.response.PriceHistoryResponse;
import com.trading.demo.stock.application.dto.response.StockResponse;
import com.trading.demo.stock.application.usecase.GetStockDetailUseCase;
import com.trading.demo.stock.application.usecase.GetStockPriceHistoryUseCase;
import com.trading.demo.stock.application.usecase.GetStocksUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockController {
    private final GetStocksUseCase getStocks;
    private final GetStockDetailUseCase getDetail;
    private final GetStockPriceHistoryUseCase getHistory;

    @GetMapping("/")
    public ApiResponse<List<StockResponse>> getStocks() {
        return new ApiResponse<List<StockResponse>>()
                .success(MarketDataConstant.GET_STOCKS_SUCCESS, getStocks.execute());
    }

    @GetMapping("/{id}")
    public ApiResponse<StockResponse> getStock(@PathVariable UUID id) {
        return new ApiResponse<StockResponse>().success(MarketDataConstant.GET_STOCK_SUCCESS, getDetail.execute(id));
    }

    @GetMapping("/{id}/price-history")
    public ApiResponse<List<PriceHistoryResponse>> getHistory(@PathVariable UUID id) {
        return new ApiResponse<List<PriceHistoryResponse>>()
                .success(MarketDataConstant.GET_PRICE_HISTORY_SUCCESS, getHistory.execute(id));
    }
}

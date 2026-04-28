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
        return ApiResponse.success(getStocks.execute(), MarketDataConstant.GET_STOCKS_SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResponse<StockResponse> getStock(@PathVariable UUID id) {
        return ApiResponse.success(getDetail.execute(id), MarketDataConstant.GET_STOCK_SUCCESS);
    }

    @GetMapping("/{id}/price-history")
    public ApiResponse<List<PriceHistoryResponse>> getHistory(@PathVariable UUID id) {
        return ApiResponse.success(getHistory.execute(id), MarketDataConstant.GET_PRICE_HISTORY_SUCCESS);
    }
}

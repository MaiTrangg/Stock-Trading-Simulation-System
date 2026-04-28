package com.trading.demo.trading.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.trading.application.dto.response.TradeDetailResponse;
import com.trading.demo.trading.application.dto.response.TradeResponse;
import com.trading.demo.trading.application.usecase.trade.GetTradeDetailUseCase;
import com.trading.demo.trading.application.usecase.trade.GetTradesUseCase;
import com.trading.demo.trading.domain.enums.OrderSide;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final GetTradesUseCase getTradesUseCase;
    private final GetTradeDetailUseCase getTradeDetailUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TradeResponse>>> getTrades(
            @RequestParam(required = false) UUID stockId,
            @RequestParam(required = false) OrderSide side,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        UUID userId = currentUserProvider.getCurrentUserId();

        List<TradeResponse> responses =
                getTradesUseCase.execute(userId, stockId, side, from, to);

        return ResponseEntity.ok(ApiResponse.success(responses, "Trades retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TradeDetailResponse>> getTradeDetail(
            @PathVariable UUID id
    ) {
        UUID userId = currentUserProvider.getCurrentUserId();

        TradeDetailResponse response =
                getTradeDetailUseCase.execute(userId, id);

        return ResponseEntity.ok(ApiResponse.success(response, "Trade retrieved successfully"));
    }
}

package com.trading.demo.portfolio.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.portfolio.application.usecase.portfolio.GetHoldingsUseCase;
import com.trading.demo.portfolio.application.usecase.portfolio.GetPortfolioSummaryUseCase;
import com.trading.demo.portfolio.application.usecase.portfolio.GetPortfolioTransactionsUseCase;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.presentation.dto.response.HoldingResponse;
import com.trading.demo.portfolio.presentation.dto.response.PortfolioSummaryResponse;
import com.trading.demo.portfolio.presentation.dto.response.PortfolioTransactionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final GetPortfolioSummaryUseCase getPortfolioSummaryUseCase;
    private final GetHoldingsUseCase getHoldingsUseCase;
    private final GetPortfolioTransactionsUseCase getPortfolioTransactionsUseCase;
    private final CurrentUserProvider currentUserProvider;


    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<PortfolioSummaryResponse>> getSummary() {

        UUID userId = currentUserProvider.getCurrentUserId();

        PortfolioSummaryResponse response =
                getPortfolioSummaryUseCase.execute(userId);

        return ResponseEntity.ok(ApiResponse.success(response, "Portfolio summary retrieved"));
    }

    @GetMapping("/holdings")
    public ResponseEntity<ApiResponse<List<HoldingResponse>>> getHoldings() {

        UUID userId = currentUserProvider.getCurrentUserId();

        List<HoldingResponse> responses =
                getHoldingsUseCase.execute(userId);

        return ResponseEntity.ok(ApiResponse.success(responses, "Holdings retrieved"));
    }


    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<PortfolioTransactionResponse>>> getTransactions(
            @RequestParam(required = false) UUID stockId,
            @RequestParam(required = false) PortfolioTransactionType type,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {

        UUID userId = currentUserProvider.getCurrentUserId();

        List<PortfolioTransactionResponse> responses =
                getPortfolioTransactionsUseCase.execute(userId, stockId, type, from, to);

        return ResponseEntity.ok(ApiResponse.success(responses, "Portfolio transactions retrieved"));
    }
}
package com.trading.demo.portfolio.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.portfolio.application.usecase.wallet.DepositUseCase;
import com.trading.demo.portfolio.application.usecase.wallet.GetFundUseCase;
import com.trading.demo.portfolio.application.usecase.wallet.GetWalletTransactionsUseCase;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.portfolio.presentation.dto.request.DepositRequest;
import com.trading.demo.portfolio.presentation.dto.response.FundResponse;
import com.trading.demo.portfolio.presentation.dto.response.WalletTransactionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final GetWalletTransactionsUseCase getWalletTransactionsUseCase;
    private final GetFundUseCase getFundUseCase;
    private final DepositUseCase depositUseCase;
    private final CurrentUserProvider currentUserProvider;


    @GetMapping
    public ResponseEntity<ApiResponse<FundResponse>> getFund() {

        UUID userId = currentUserProvider.getCurrentUserId();

        FundResponse response = getFundUseCase.execute(userId);

        return ResponseEntity.ok(ApiResponse.success(response, "Fund retrieved"));
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<Void>> deposit(
            @RequestBody DepositRequest request
    ) {

        UUID userId = currentUserProvider.getCurrentUserId();

        depositUseCase.execute(userId, request.getAmount());

        return ResponseEntity.ok(ApiResponse.success(null, "Deposit successful"));
    }

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<WalletTransactionResponse>>> getTransactions(
            @RequestParam(required = false) WalletTransactionType type,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {

        UUID userId = currentUserProvider.getCurrentUserId();

        List<WalletTransactionResponse> responses =
                getWalletTransactionsUseCase.execute(userId, type, from, to);

        return ResponseEntity.ok(ApiResponse.success(responses, "Wallet transactions retrieved"));
    }
}

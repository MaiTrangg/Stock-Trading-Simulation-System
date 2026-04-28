package com.trading.demo.portfolio.application.usecase.wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.application.mapper.WalletTransactionResponseMapper;
import com.trading.demo.portfolio.domain.port.WalletTransactionPort;
import org.springframework.stereotype.Service;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.model.WalletTransaction;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.portfolio.infrastructure.persistence.mapper.WalletTransactionMapper;
import com.trading.demo.portfolio.presentation.dto.response.WalletTransactionResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GetWalletTransactionsUseCase {

    private final WalletTransactionResponseMapper mapper;
    private final WalletTransactionPort walletTransactionPort;

    public List<WalletTransactionResponse> execute(
            UUID userId,
            WalletTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    ) {

        if (userId == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }


        List<WalletTransaction> transactions =
                walletTransactionPort.findTransactions(userId, type, from, to);

        return transactions.stream()
                .map(mapper::toResponse)
                .toList();
    }
}

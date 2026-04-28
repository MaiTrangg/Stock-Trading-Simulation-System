package com.trading.demo.portfolio.application.usecase.portfolio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import com.trading.demo.portfolio.domain.model.PortfolioTransaction;
import com.trading.demo.portfolio.domain.port.PortfolioTransactionPort;
import com.trading.demo.portfolio.presentation.dto.response.PortfolioTransactionResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GetPortfolioTransactionsUseCase {

    private final PortfolioTransactionPort transactionPort;

    public List<PortfolioTransactionResponse> execute(
            UUID userId,
            UUID stockId,
            PortfolioTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    ) {

        List<PortfolioTransaction> transactions =
                transactionPort.findTransactions(
                        userId,
                        stockId,
                        type,
                        from,
                        to
                );

        return transactions.stream()
                .map(t -> PortfolioTransactionResponse.builder()
                        .id(t.getId())
                        .stockId(t.getStockId())
                        .quantity(t.getQuantity())
                        .type(t.getTransactionType().name())
                        .referenceId(t.getReferenceId())
                        .createdAt(t.getCreatedAt())
                        .build())
                .toList();
    }
}

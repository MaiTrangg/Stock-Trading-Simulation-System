package com.trading.demo.portfolio.application.usecase.wallet;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.util.ReferenceIdGenerator;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.port.WalletPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositUseCase {

    private final WalletPort walletPort;

    public void execute(UUID userId, BigDecimal amount) {

        if (userId == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        // idempotency (important)
        UUID referenceId = ReferenceIdGenerator.generate(
                userId,
                UUID.randomUUID(), // moi lan deposit la 1 event rieng
                WalletTransactionType.RESERVE.name()
        );

        walletPort.deposit(userId, amount, referenceId);
    }
}

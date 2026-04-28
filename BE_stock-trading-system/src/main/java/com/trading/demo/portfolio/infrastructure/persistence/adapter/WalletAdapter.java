package com.trading.demo.portfolio.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.portfolio.domain.model.WalletTransaction;
import com.trading.demo.portfolio.infrastructure.persistence.entity.WalletTransactionEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.portfolio.domain.port.WalletTransactionPort;
import com.trading.demo.portfolio.infrastructure.persistence.mapper.WalletMapper;
import com.trading.demo.portfolio.infrastructure.persistence.repository.WalletJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WalletAdapter implements WalletPort {

    private final WalletJpaRepository walletRepo;
    private final WalletTransactionPort txPort;
    private final WalletMapper mapper;


    @Override
    @Transactional
    public void reserve(UUID userId, BigDecimal amount, UUID referenceId) {
        // LUONG: Dat lenh mua.
        // Giam available_balance, Tang reserved_balance. (balance giu nguyen)
        int rows = walletRepo.reserveMoney(userId, amount);
        if (rows == 0) {
            throw new IllegalStateException("So du kha dung khong du");
        }

        // Log transaction with idempotency using DB constraint
        try {
            txPort.logTransaction(userId, WalletTransactionType.RESERVE, amount, referenceId);
        } catch (DuplicateKeyException e) {
            log.info("Transaction already processed (idempotent): {}", referenceId);
            // Transaction already processed - this is expected for retries
        }
    }

    @Override
    @Transactional
    public void release(UUID userId, BigDecimal amount, UUID referenceId) {
        // LUONG: Khop lenh mua thanh cong.
        // Giam balance (tong tien thuc te), Giam reserved_balance (tien cho).
        int rows = walletRepo.decreaseReservedAndTotalBalance(userId, amount);

        if (rows == 0) {
            throw new IllegalStateException("Reserved balance khong du");
        }

        // Log transaction with idempotency using DB constraint
        try {
            txPort.logTransaction(userId, WalletTransactionType.RELEASE, amount, referenceId);
        } catch (DuplicateKeyException e) {
            log.info("Transaction already processed (idempotent): {}", referenceId);
            // Transaction already processed - this is expected for retries
        }
    }

    @Override
    @Transactional
    public void addBalance(UUID userId, BigDecimal amount, UUID referenceId) {
        // LUONG: Khop lenh ban thanh cong.
        // Tang balance (tong tien thuc te), Tang available_balance (tien co the dung ngay).
        int rows = walletRepo.increaseAvailableAndTotalBalance(userId, amount);

        if (rows == 0) {
            throw new IllegalStateException("Update balance failed");
        }

        // Log transaction with idempotency using DB constraint
        try {
            txPort.logTransaction(userId, WalletTransactionType.TRADE_SELL, amount, referenceId);
        } catch (DuplicateKeyException e) {
            log.info("Transaction already processed (idempotent): {}", referenceId);
            // Transaction already processed - this is expected for retries
        }
    }

    @Override
    @Transactional
    public void refund(UUID userId, BigDecimal amount, UUID referenceId) {
        // LUONG: Huy lenh mua.
        // Tang available_balance, Giam reserved_balance.
        int rows = walletRepo.refundToAvailable(userId, amount);

        if (rows == 0) {
            throw new IllegalStateException("Refund failed");
        }

        // Log transaction with idempotency using DB constraint
        try {
            txPort.logTransaction(userId, WalletTransactionType.REFUND, amount, referenceId);
        } catch (DuplicateKeyException e) {
            log.info("Transaction already processed (idempotent): {}", referenceId);
            // Transaction already processed - this is expected for retries
        }
    }

    @Override
    public Optional<Fund> findByUserIdAndIsDeleteFalse(UUID userId) {
        return walletRepo.findByUserIdAndIsDeleteFalse(userId).map(mapper::toDomain);
    }

    @Override
    public Fund save(Fund fund) {
        return mapper.toDomain(walletRepo.save(mapper.toEntity(fund)));
    }

    @Override
    @Transactional
    public void deposit(UUID userId, BigDecimal amount, UUID referenceId) {
        // ===== validate =====
        if (userId == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.INVALID_AMOUNT);
        }

        try {
            // ===== atomic update =====
            int updated = walletRepo.deposit(userId, amount);

            if (updated == 0) {
                throw new AppException(ErrorCode.FUND_NOT_FOUND);
            }
            txPort.logTransaction(userId, WalletTransactionType.DEPOSIT, amount, referenceId);

        } catch (DataIntegrityViolationException e) {
            // duplicate referenceId ? request bi retry ? bo qua
            log.info("[DEPOSIT] Transaction already processed (idempotent): {}", referenceId);
            return;
        }
    }


}

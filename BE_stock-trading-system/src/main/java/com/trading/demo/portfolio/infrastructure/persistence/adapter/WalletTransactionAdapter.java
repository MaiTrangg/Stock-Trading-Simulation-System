package com.trading.demo.portfolio.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.application.mapper.WalletTransactionResponseMapper;
import org.springframework.stereotype.Repository;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.model.WalletTransaction;
import com.trading.demo.portfolio.domain.port.WalletTransactionPort;
import com.trading.demo.portfolio.infrastructure.persistence.entity.WalletTransactionEntity;
import com.trading.demo.portfolio.infrastructure.persistence.mapper.WalletTransactionMapper;
import com.trading.demo.portfolio.infrastructure.persistence.repository.WalletTransactionJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class WalletTransactionAdapter implements WalletTransactionPort {

    private final WalletTransactionJpaRepository txRepo;
    private final WalletTransactionMapper txMapper; // Them mapper vao day

    @Override
    @Transactional
    public void logTransaction(UUID userId, WalletTransactionType type, BigDecimal amount, UUID referenceId) {
        // 1. Tao Domain Model truoc
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .userId(userId)
                .type(type)
                .amount(amount)
                .referenceId(referenceId)
                .createdAt(LocalDateTime.now())
                .build();

        // 2. Dung Mapper chuyen sang Entity (MapStruct se tu xu ly convert Enum sang String)
        WalletTransactionEntity entity = txMapper.toEntity(walletTransaction);

        // 3. Gan ID neu Mapper chua lam
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }

        txRepo.save(entity);
    }

    @Override
    public boolean existsByReferenceId(UUID referenceId) {
        return txRepo.existsByReferenceId(referenceId);
    }

    @Override
    public List<WalletTransaction> findTransactions(
            UUID userId,
            WalletTransactionType type,
            LocalDateTime from,
            LocalDateTime to
    ) {

        List<WalletTransactionEntity> entities =
                txRepo.search(userId, type, from, to);

        // NEVER return null
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(txMapper::toDomain)
                .toList();
    }
}

package com.trading.demo.portfolio.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.demo.portfolio.infrastructure.persistence.entity.WalletTransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletTransactionJpaRepository extends JpaRepository<WalletTransactionEntity, UUID> {
    boolean existsByReferenceId(UUID referenceId);

    List<WalletTransactionEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    @Query("""
                SELECT w FROM WalletTransactionEntity w
                WHERE w.userId = :userId
                AND (:type IS NULL OR w.type = :type)
                AND (w.createdAt >= COALESCE(:from, w.createdAt))
                AND (w.createdAt <= COALESCE(:to, w.createdAt))
                ORDER BY w.createdAt DESC
            """)
    List<WalletTransactionEntity> search(
            @Param("userId") UUID userId,
            @Param("type") WalletTransactionType type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}

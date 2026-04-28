package com.trading.demo.portfolio.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.demo.portfolio.infrastructure.persistence.entity.PortfolioTransactionEntity;

public interface PortfolioTransactionJpaRepository extends JpaRepository<PortfolioTransactionEntity, UUID> {
    // Repository for portfolio transactions with unique constraint on reference_id
    // The unique constraint is enforced at database level
}

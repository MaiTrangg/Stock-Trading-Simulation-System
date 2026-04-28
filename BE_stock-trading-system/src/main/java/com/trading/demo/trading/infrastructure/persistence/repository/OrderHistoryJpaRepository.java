package com.trading.demo.trading.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.demo.trading.infrastructure.persistence.entity.OrderHistoryEntity;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistoryEntity, UUID> {
    List<OrderHistoryEntity> findByOrderIdOrderByChangedAtAsc(UUID orderId);
}

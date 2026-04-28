package com.trading.demo.trading.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.OrderHistory;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.infrastructure.persistence.entity.OrderHistoryEntity;
import com.trading.demo.trading.infrastructure.persistence.repository.OrderHistoryJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderHistoryAdapter implements OrderHistoryRepository {

    private final OrderHistoryJpaRepository jpaRepository;

    @Override
    @Transactional
    public void save(UUID orderId, OrderStatus oldStatus, OrderStatus newStatus, UUID changedBy) {
        OrderHistoryEntity entity = OrderHistoryEntity.builder()
                .id(UUID.randomUUID())
                .orderId(orderId)
                .oldStatus(oldStatus != null ? oldStatus.name() : null)
                .newStatus(newStatus.name())
                .changedBy(changedBy)
                .changedAt(LocalDateTime.now())
                .build();

        jpaRepository.save(entity);
    }
    
    @Override
    public List<OrderHistory> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderIdOrderByChangedAtAsc(orderId)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    
    private OrderHistory toDomain(OrderHistoryEntity entity) {
        return OrderHistory.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .oldStatus(entity.getOldStatus() != null ? 
                    OrderStatus.valueOf(entity.getOldStatus()) : null)
                .newStatus(OrderStatus.valueOf(entity.getNewStatus()))
                .changedBy(entity.getChangedBy())
                .createdAt(entity.getChangedAt())
                .build();
    }
}

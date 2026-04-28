package com.trading.demo.trading.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderRepository;
import com.trading.demo.trading.infrastructure.persistence.mapper.OrderMapper;
import com.trading.demo.trading.infrastructure.persistence.repository.OrderJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpa;
    private final OrderMapper mapper;

    @Override
    public void save(Order order) {
        jpa.save(mapper.toEntity(order));
    }

    @Override
    public Order findById(UUID id) {
        return jpa.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public List<Order> findBySymbolAndStatusIn(String symbol, List<OrderStatus> statuses) {
        return jpa
                .findBySymbolAndStatusIn(symbol, statuses)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

//    @Override
//    public int executeMatchAtomic(UUID orderId, int matchQty, Long version) {
//        return jpa.executeMatchAtomic(orderId, matchQty, version);
//    }

    @Override
    public int executeCancelAtomic(UUID orderId, Long version,
                                   OrderStatus canceledStstus,
                                   List<OrderStatus> allowedStatuses) {
        return jpa.executeCancelAtomic(orderId, version, canceledStstus, allowedStatuses);
    }

//    @Override
//    public List<Order> findByUserId(UUID userId) {
//        return jpa.findByUserId(userId)
//                .stream()
//                .map(mapper::toDomain)
//                .toList();
//    }

    @Override
    public List<Order> search(UUID userId,
                              OrderStatus status,
                              UUID stockId,
                              OrderSide side,
                              LocalDateTime from,
                              LocalDateTime to) {
        return jpa.search(userId, status, stockId, side, from, to)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findOpenOrders(UUID userId, UUID stockId, List<OrderStatus> openStatuses) {
        return jpa.findOpenOrders(userId, stockId, openStatuses)
                .stream().map(mapper::toDomain)
                .toList();
    }

//    @Override
//    public List<Order> findByUserIdAndStatus(UUID userId, OrderStatus status) {
//        return jpa.findByUserIdAndStatus(userId, status)
//                .stream()
//                .map(mapper::toDomain)
//                .toList();
//    }
}

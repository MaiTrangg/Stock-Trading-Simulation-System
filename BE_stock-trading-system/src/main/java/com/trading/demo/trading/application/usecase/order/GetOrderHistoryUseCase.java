package com.trading.demo.trading.application.usecase.order;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.trading.application.dto.response.OrderHistoryResponse;
import com.trading.demo.trading.application.mapper.OrderResponseMapper;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrderHistoryUseCase {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderResponseMapper orderResponseMapper;

    public List<OrderHistoryResponse> execute(UUID userId, UUID orderId) {

        if (userId == null || orderId == null) {
            throw new AppException(ErrorCode.INVALID_ORDER_PARAMETERS);
        }

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        if (!order.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        return orderHistoryRepository.findByOrderId(orderId)
                .stream()
                .map(orderResponseMapper::toHistoryResponse)
                .toList();
    }
}
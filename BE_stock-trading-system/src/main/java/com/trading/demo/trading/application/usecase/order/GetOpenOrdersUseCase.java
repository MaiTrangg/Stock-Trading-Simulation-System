package com.trading.demo.trading.application.usecase.order;

import com.trading.demo.trading.application.dto.response.OrderResponse;
import com.trading.demo.trading.application.mapper.OrderResponseMapper;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOpenOrdersUseCase {
    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderMapper;

    public List<OrderResponse> execute(UUID userId, UUID stockId) {
        List<OrderStatus> openStatuses = List.of(
                OrderStatus.PENDING,
                OrderStatus.PARTIALLY_FILLED
        );
        return orderRepository.findOpenOrders(userId, stockId, openStatuses)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }
}

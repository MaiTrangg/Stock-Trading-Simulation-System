package com.trading.demo.trading.application.usecase.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import org.springframework.stereotype.Service;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.trading.application.dto.response.OrderResponse;
import com.trading.demo.trading.application.mapper.OrderResponseMapper;
import com.trading.demo.trading.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrdersUseCase {

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderMapper;

    public List<OrderResponse> execute(UUID userId,
                                       OrderStatus status,
                                       UUID stockId,
                                       OrderSide side,
                                       LocalDateTime from,
                                       LocalDateTime to) {

        if (userId == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return orderRepository.search(userId, status, stockId, side, from, to)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }
}

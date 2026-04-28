package com.trading.demo.trading.application.usecase.order;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.trading.application.dto.response.TradeResponse;
import com.trading.demo.trading.application.mapper.TradeResponseMapper;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.port.TradePort;
import com.trading.demo.trading.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrderTradesUseCase {
    private final TradeResponseMapper tradeResponseMapper;
    private final OrderRepository orderRepository;
    private final TradePort tradePort;

    public List<TradeResponse> execute(UUID orderId, UUID userId) {

        Order order = orderRepository.findById(orderId);

        if (!order.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        return tradePort.findByOrderId(orderId)
                .stream()
                .map(tradeResponseMapper::toResponse)
                .toList();
    }
}

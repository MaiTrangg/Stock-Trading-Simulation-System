package com.trading.demo.trading.application.usecase.order;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.demo.common.entity.OutboxEvent;
import com.trading.demo.common.enums.OutboxStatus;
import com.trading.demo.common.event.EventPublisher;
import com.trading.demo.common.repository.OutboxEventRepository;
import com.trading.demo.trading.application.event.OrderCreatedEvent;
import com.trading.demo.portfolio.application.service.PortfolioService;
import com.trading.demo.portfolio.application.service.WalletService;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.trading.application.dto.request.PlaceOrderRequest;
import com.trading.demo.trading.application.dto.response.OrderResponse;
import com.trading.demo.trading.domain.service.MatchingService;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderType;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.domain.repository.OrderRepository;
import com.trading.demo.trading.application.mapper.OrderResponseMapper;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.enums.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;
    private final WalletPort walletPort;
    private final OrderResponseMapper orderMapper;
    private final MatchingService matchingService;
    private final StockRepository stockRepo;
    private final PortfolioPort portfolioPort;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final WalletService walletService;
    private final PortfolioService portfolioService;
    private final EventPublisher eventPublisher;

    @Transactional
    public OrderResponse execute(UUID userId, PlaceOrderRequest req) {

        Order order = Order.create(
                userId,
                req.getStockId(),
                OrderSide.valueOf(req.getSide().toUpperCase()),
                OrderType.valueOf(req.getType().toUpperCase()),
                req.getPrice(),
                req.getQuantity()
        );

        walletService.ensureFund(userId);
        portfolioService.ensurePortfolio(userId, order.getStockId());
        // 2. phong toa tai san
        if (order.getSide() == OrderSide.BUY) {
            walletPort.reserve(userId, order.totalAmount(), order.getId());
        } else {
            // lock co phieu neu la lenh BAN
            portfolioPort.lockStock(userId, order.getStockId(), order.getId(), order.getQuantity());
        }

        // 3. luu Order
        orderRepository.save(order);

        // 4. Luu History
        historyRepository.save(order.getId(), order.getStatus(), order.getStatus(), userId);

        // 4. Save Outbox Event (atomic with order)
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getStockId());
        eventPublisher.publish(event);
        try {
            OutboxEvent outbox = OutboxEvent.builder()
                    .eventId(event.getEventId())
                    .eventType("OrderCreated")
                    .aggregateType("Order")
                    .aggregateId(order.getId())
                    .payload(objectMapper.valueToTree(event))
                    .status(OutboxStatus.PENDING)
                    .retryCount(0)
                    .build();
            outboxRepository.save(outbox);
        } catch (Exception e) {
            log.error("ERROR when saving outbox", e);
            throw new AppException(ErrorCode.ORDER_CREATION_FAILED);
        }

        return orderMapper.toResponse(order);
    }
}

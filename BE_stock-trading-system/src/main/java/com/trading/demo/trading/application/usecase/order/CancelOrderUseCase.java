package com.trading.demo.trading.application.usecase.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.PortfolioTransactionType;
import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.util.ReferenceIdGenerator;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.trading.application.dto.response.OrderResponse;
import com.trading.demo.trading.application.mapper.OrderResponseMapper;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancelOrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;
    private final WalletPort walletPort;
    private final PortfolioPort portfolioPort;
    private final OrderResponseMapper orderMapper;

    @Transactional
    public OrderResponse cancel(UUID orderId, UUID userId) {
        // 1. Get order by ID
        Order order = orderRepository.findById(orderId);

        // 2. Validate order ownership
        if (!order.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        // 3. Handle idempotent case - already cancelled
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.info("Order already cancelled: {}", orderId);
            return orderMapper.toResponse(order);
        }

        // 4. Reject filled orders
        if (order.getStatus() == OrderStatus.FILLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_FILLED);
        }

        // 5. Store old status for history
        OrderStatus oldStatus = order.getStatus();

        // 6. Atomic update with optimistic locking
        int updated = orderRepository.executeCancelAtomic
                (orderId, order.getVersion(), OrderStatus.CANCELLED,
                        List.of(OrderStatus.PENDING, OrderStatus.PARTIALLY_FILLED));

        if (updated == 0) {
            throw new AppException(ErrorCode.CANCEL_ORDER_FAILED);
        }

        // 7. Refresh order to get latest state
        Order updatedOrder = orderRepository.findById(orderId);

        // 8. Process financial operations with idempotency (use original order for calculations)
        processCancelFinancials(order, oldStatus);

        // 9. Save order history
        historyRepository.save(orderId, oldStatus, updatedOrder.getStatus(), userId);

        log.info("CANCELLED SUCCESS: Order No: {}, Status: {}, User: {}",
                updatedOrder.getOrderNo(), updatedOrder.getStatus(), userId);

        return orderMapper.toResponse(updatedOrder);
    }

    private void processCancelFinancials(Order order, OrderStatus oldStatus) {
        // Only process financial operations if order was PENDING or PARTIALLY_FILLED
        if (oldStatus == OrderStatus.PENDING || oldStatus == OrderStatus.PARTIALLY_FILLED) {

            if (order.getSide() == OrderSide.BUY) {
                // BUY order: refund remaining amount
                BigDecimal remainingAmount = order.getRemainingAmount();

                if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
                    // Generate deterministic reference ID for cancel refund
                    UUID cancelRefundReferenceId = ReferenceIdGenerator.generate(
                            order.getId(), WalletTransactionType.CANCEL_REFUND.name());

                    walletPort.refund(order.getUserId(), remainingAmount, cancelRefundReferenceId);
                    log.info("Refunded amount: {} for order: {} with reference: {}",
                            remainingAmount, order.getId(), cancelRefundReferenceId);
                }

            } else {
                // SELL order: release remaining stock
                int remainingQuantity = order.getRemainingQuantity();

                if (remainingQuantity > 0) {
                    // Generate deterministic reference ID for stock release
                    UUID releaseStockReferenceId = ReferenceIdGenerator.generate(
                            order.getUserId(), order.getStockId(), order.getId(),
                            PortfolioTransactionType.RELEASE_STOCK.name());

                    portfolioPort.releaseStock(order.getUserId(), order.getStockId(), order.getId(), remainingQuantity);
                    log.info("Released stock: {} for order: {} with reference: {}",
                            remainingQuantity, order.getId(), releaseStockReferenceId);
                }
            }
        }
    }
}

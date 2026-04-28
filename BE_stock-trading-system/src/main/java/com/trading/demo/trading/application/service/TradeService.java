package com.trading.demo.trading.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.common.util.ReferenceIdGenerator;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.domain.port.TradePort;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;
    private final WalletPort walletPort;
    private final PortfolioPort portfolioPort;
    private final TradePort tradePort;
    private static final UUID SYSTEM_MARKET_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executeTrade(Order order, BigDecimal matchPrice, int actualMatchQty) {
        OrderStatus oldStatus = order.getStatus();
        log.info("[TRADE-START] orderId={}, price={}, qty={}",
                order.getId(),
                matchPrice,
                actualMatchQty
        );

        // 1. Cap nhat trang thai Order trong Domain dua tren so thuc khop
        order.executeMatch(actualMatchQty);
        orderRepository.save(order);

        log.info("[ORDER-UPDATED] newStatus={}, executedQty={}, remainingQty={}",
                order.getStatus(),
                order.getExecutedQuantity(),
                order.getRemainingQuantity()
        );

        // 2. Tinh toan tong tien dua tren so luong THUC KHOP
        BigDecimal actualAmount = matchPrice.multiply(BigDecimal.valueOf(actualMatchQty));

        log.info("[TRADE-AMOUNT] actualAmount={}", actualAmount);

        // 3. Hoan doi tai san (Atomic Swap)
        if (order.getSide() == OrderSide.BUY) {
            // tinh tien reserve theo gia order
            BigDecimal reservedAmount =
                    order.getPrice().multiply(BigDecimal.valueOf(actualMatchQty));

            BigDecimal refund =
                    reservedAmount.subtract(actualAmount);

            // Generate reference IDs for idempotency
            UUID tradeReferenceId = ReferenceIdGenerator.generate(order.getUserId(), order.getId(),
                    WalletTransactionType.RELEASE.name());
            UUID refundReferenceId = ReferenceIdGenerator.generate(order.getUserId(), order.getId(),
                    WalletTransactionType.REFUND.name());

            // tru tien that - with proper reference ID
            walletPort.release(order.getUserId(), actualAmount, tradeReferenceId);

            // refund tien du - with proper reference ID
            if (refund.compareTo(BigDecimal.ZERO) > 0) {
                walletPort.refund(order.getUserId(), refund, refundReferenceId);
            }

            portfolioPort.updateAfterTrade(
                    order.getUserId(),
                    order.getStockId(),
                    order.getId(),
                    actualMatchQty,
                    matchPrice
            );

        } else {
            // Generate reference ID for sell proceed
            UUID sellProceedReferenceId = ReferenceIdGenerator
                    .generate(order.getUserId(), order.getId(), WalletTransactionType.TRADE_SELL.name());

            walletPort.addBalance(order.getUserId(), actualAmount, sellProceedReferenceId);

            portfolioPort.updateAfterTrade(
                    order.getUserId(),
                    order.getStockId(),
                    order.getId(),
                    -actualMatchQty,
                    matchPrice
            );
        }

        // 4. Tao Trade Model de luu vet giao dich
        Trade trade = Trade.builder()
                .id(UUID.randomUUID())
                .orderId(order.getId())
                .stockId(order.getStockId())
                .side(order.getSide().name())
                .price(matchPrice)
                .quantity(actualMatchQty)
                .totalAmount(matchPrice.multiply(BigDecimal.valueOf(actualMatchQty)))
                .executedAt(LocalDateTime.now())
                .buyerId(order.getSide() == OrderSide.BUY ? order.getUserId() : SYSTEM_MARKET_ID)
                .sellerId(order.getSide() == OrderSide.SELL ? order.getUserId() : SYSTEM_MARKET_ID)
                .build();

        // Check trade deduplication before saving
        if (!tradePort.existsByOrderIdAndPriceAndQuantity(order.getId(), matchPrice, actualMatchQty)) {
            tradePort.saveTrade(trade);
        }

        // 5. Luu lich su thay doi trang thai lenh
        historyRepository.save(order.getId(), oldStatus, order.getStatus(), order.getUserId());

        log.info("MATCHED SUCCESS: Order No: {}, Qty: {}, Price: {}, Status: {}",
                order.getOrderNo(), actualMatchQty, matchPrice, order.getStatus());
    }
}

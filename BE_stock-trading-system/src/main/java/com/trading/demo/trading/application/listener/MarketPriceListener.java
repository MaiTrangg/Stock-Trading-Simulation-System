package com.trading.demo.trading.application.listener;

import com.trading.demo.common.repository.ProcessedEventRepository;
import com.trading.demo.trading.application.event.MarketPriceUpdatedEvent;
import com.trading.demo.trading.domain.service.MatchingService;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarketPriceListener {

    private final OrderRepository orderRepository;
    private final MatchingService matchingService;
    private final ProcessedEventRepository processedEventRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(MarketPriceUpdatedEvent event) {

        // Check deduplication
        if (processedEventRepository.existsByEventId(event.getEventId().toString())) {
            return; // bo event trung lap
        }

        String symbol = event.getSymbol();

        // Lay tat ca order co the match lai
        List<Order> orders = orderRepository.findBySymbolAndStatusIn(
                symbol,
                List.of(OrderStatus.PENDING, OrderStatus.PARTIALLY_FILLED)
        );

        if (orders.isEmpty()) {
            return;
        }

        log.info("RE-MATCH {} orders for {}", orders.size(), symbol);

        for (Order order : orders) {
            try {
                // Check order status truoc khi match
                if (order.getStatus() == OrderStatus.FILLED) {
                    continue; // bo qua order da filled
                }

                matchingService.matchImmediate(order, symbol);
            } catch (Exception e) {
                log.error("Match failed for order {}", order.getId(), e);
            }
        }

        // MARK AS PROCESSED
        processedEventRepository.saveByEventId(event.getEventId().toString());
    }
}

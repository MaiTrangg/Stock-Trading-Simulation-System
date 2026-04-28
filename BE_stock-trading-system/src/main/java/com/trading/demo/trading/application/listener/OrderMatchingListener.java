package com.trading.demo.trading.application.listener;

import com.trading.demo.common.repository.ProcessedEventRepository;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.trading.application.event.OrderCreatedEvent;
import com.trading.demo.trading.domain.service.MatchingService;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMatchingListener {

    private final MatchingService matchingService;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepo;
    private final ProcessedEventRepository processedEventRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info("vao orderMatchingListenner");

        // Check deduplication
        if (processedEventRepository.existsByEventId(event.getEventId().toString())) {
            return; // bỏ qua event trùng lặp
        }

        Order order = orderRepository.findById(event.getOrderId());

        String symbol = stockRepo.findSymbolById(order.getStockId());

        matchingService.matchImmediate(order, symbol);

        // MARK AS PROCESSED
        processedEventRepository.saveByEventId(event.getEventId().toString());
        log.info("end orderMatchingListenner");
    }
}

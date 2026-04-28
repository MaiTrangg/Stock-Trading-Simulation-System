package com.trading.demo.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.demo.common.entity.OutboxEvent;
import com.trading.demo.common.enums.OutboxStatus;
import com.trading.demo.common.repository.OutboxEventRepository;
import com.trading.demo.common.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxDispatcher {

    private final OutboxEventRepository outboxRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000) // Poll every second
    public void processPendingEvents() {
        log.info("vao processPendingEvents method in OutboxDispatcher");
        List<OutboxEvent> pendingEvents = outboxRepository
                .findByStatusOrderByCreatedAt(OutboxStatus.PENDING);

        if (pendingEvents.isEmpty()) {
            return;
        }

        log.info("Processing {} pending outbox events", pendingEvents.size());

        for (OutboxEvent event : pendingEvents) {
            try {
                processEvent(event);
            } catch (Exception e) {
                log.error("Failed to process outbox event: {}", event.getId(), e);
            }
        }
        log.info("vao processPendingEvents method in OutboxDispatcher");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processEvent(OutboxEvent event) {
        log.info("vao processEvent method in OutboxDispatcher");
        try {
            // 1. Check if already processed (idempotency)
            if (processedEventRepository.existsByEventId(event.getEventId().toString())) {
                markAsSent(event);
                log.info("Event {} already processed, skipping", event.getEventId());
                return;
            }

            // 2. Process event based on type
            switch (event.getEventType()) {
                case "OrderCreated":
                    processOrderCreatedEvent(event);
                    break;
                case "MarketPriceUpdated":
                    processMarketPriceUpdatedEvent(event);
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }

            // 3. Mark as processed
            processedEventRepository.saveByEventId(event.getEventId().toString());

            // 4. Mark outbox as sent
            markAsSent(event);

            log.info("Successfully processed outbox event: {}", event.getEventId());

        } catch (Exception e) {
            event.setRetryCount(event.getRetryCount() + 1);
            event.setLastError(e.getMessage());

            if (event.getRetryCount() >= 3) {
                event.setStatus(OutboxStatus.FAILED);
                log.error("Event {} failed after 3 retries, marking as FAILED", event.getEventId());
            }

            outboxRepository.save(event);
            throw e;
        }
        log.info("end processEvent method in OutboxDispatcher");
    }

    private void processOrderCreatedEvent(OutboxEvent event) {
        // Publish to external event bus or process directly
        log.info("Processing OrderCreated event for aggregate: {}", event.getAggregateId());
        // Here you would publish to Kafka/RabbitMQ/etc.
    }

    private void processMarketPriceUpdatedEvent(OutboxEvent event) {
        // Publish to external event bus or process directly
        log.info("Processing MarketPriceUpdated event for symbol: {}", event.getAggregateId());
        // Here you would publish to Kafka/RabbitMQ/etc.
    }

    private void markAsSent(OutboxEvent event) {
        event.setStatus(OutboxStatus.SENT);
        event.setProcessedAt(java.time.LocalDateTime.now());
        outboxRepository.save(event);
    }
}

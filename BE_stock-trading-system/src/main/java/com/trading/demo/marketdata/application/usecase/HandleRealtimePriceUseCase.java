package com.trading.demo.marketdata.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.trading.demo.marketdata.domain.model.LastPriceSnapshot;
import org.springframework.stereotype.Service;

import com.trading.demo.common.event.EventPublisher;
import com.trading.demo.marketdata.application.service.StockPriceHistoryService;
import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.PriceCachePort;
import com.trading.demo.marketdata.domain.port.out.PricePushPort;
import com.trading.demo.stock.domain.repository.StockRepository;
import com.trading.demo.trading.application.event.MarketPriceUpdatedEvent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandleRealtimePriceUseCase {

    private final PriceCachePort cache;
    private final PricePushPort push;
    private final StockPriceHistoryService historyService;
    private final StockRepository stockRepo;
    private final EventPublisher eventPublisher;

    // snapshot cache (gop 3 map ? tranh lech data)
    private final Map<String, LastPriceSnapshot> lastMap = new ConcurrentHashMap<>();
    private final Map<String, UUID> localStockIdMap = new ConcurrentHashMap<>();

    public void handle(MarketPrice price) {

        String symbol = price.getSymbol();

        // dam bao atomic theo tung symbol
        synchronized (symbol.intern()) {

            LastPriceSnapshot last = lastMap.get(symbol);

            boolean isDuplicate = isSameSnapshot(last, price);

            // v?n update snapshot
            lastMap.put(symbol, new LastPriceSnapshot(
                    price.getClose(),
                    price.getBestBidPrice(),
                    price.getBestAskPrice()
            ));

            // 1. cache
            cache.put(price);

            // 2. LUÔN publish event (QUAN TR?NG)
            eventPublisher.publish(
                    new MarketPriceUpdatedEvent(
                            symbol,
                            price.getBestBidPrice(),
                            price.getBestAskPrice(),
                            price.getBestBidQuantity(),
                            price.getBestAskQuantity()
                    )
            );

            // 3. push realtime
            push.push(price);

            // 4. ch? skip history n?u duplicate
            if (!isDuplicate) {
                UUID stockId = getStockIdWithCache(symbol);
                if (stockId != null) {
                    historyService.saveHistoryAsync(stockId, price);
                }
            }


            log.info("UPDATED PRICE: {} = {}", symbol, price.getClose());
        }
    }

    // ================= HELPER =================

    private boolean isSameSnapshot(LastPriceSnapshot last, MarketPrice price) {
        if (last == null) {
            return false;
        }

        return isSame(last.getClose(), price.getClose())
                && isSame(last.getBid(), price.getBestBidPrice())
                && isSame(last.getAsk(), price.getBestAskPrice());
    }

    private boolean isSame(BigDecimal a, BigDecimal b) {
        return a != null && b != null && a.compareTo(b) == 0;
    }

    private UUID getStockIdWithCache(String symbol) {
        return localStockIdMap.computeIfAbsent(symbol, s -> {
            UUID id = stockRepo.findIdBySymbol(s);
            if (id == null) {
                log.error("Stock not found: {}", s);
                return null;
            }
            return id;
        });
    }
}

package com.trading.demo.marketdata.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.model.StockPriceHistory;
import com.trading.demo.marketdata.domain.port.out.PriceCachePort;
import com.trading.demo.marketdata.domain.port.out.PricePushPort;
import com.trading.demo.marketdata.domain.port.out.StockPriceHistoryRepositoryPort;
import com.trading.demo.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandleRealtimePriceUseCase {

    private final PriceCachePort cache;
    private final PricePushPort push;
    private final StockPriceHistoryRepositoryPort priceHistoryRepo;
    private final StockRepository stockRepo;

    private final Map<String, BigDecimal> lastPriceMap = new ConcurrentHashMap<>();

    public void handle(MarketPrice price) {

        BigDecimal last = lastPriceMap.get(price.getSymbol());

        if (last != null && last.compareTo(price.getClose()) == 0) {
            log.debug("SKIP DUPLICATE: {}", price.getSymbol());
            return;
        }

        lastPriceMap.put(price.getSymbol(), price.getClose());

        // 1. cache
        cache.put(price);

        // get stockId
        UUID stockId = stockRepo
                .findIdBySymbol(price.getSymbol());

        // avoid duplicate DB
        LocalDate today = price.getTimestamp().toLocalDate();
        if (!priceHistoryRepo.existsByStockIdAndPriceDate(stockId, today)) {

            StockPriceHistory history =
                    StockPriceHistory.fromMarketPrice(stockId, price);

            priceHistoryRepo.save(history);
        }

        // 3. push realtime
        push.push(price);

        log.info("UPDATED PRICE: {} = {}", price.getSymbol(), price.getClose());
    }
}

package com.trading.demo.trading.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.common.util.ReferenceIdGenerator;
import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.PriceCachePort;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.domain.port.TradePort;
import com.trading.demo.trading.domain.repository.OrderHistoryRepository;
import com.trading.demo.trading.domain.repository.OrderRepository;
import com.trading.demo.trading.domain.service.MatchingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingEngine implements MatchingService {

    private final PriceCachePort priceCache;
    private final TradeService tradeService;


    /**
     * Khop lenh ngay lap tuc khi vua dat lenh (Dung trong PlaceOrderUseCase)
     */
    @Transactional
    @Override
    public void matchImmediate(Order order, String symbol) {
        log.info("[MATCH-START] orderId={}, symbol={}, status={}, side={}, price={}, remainingQty={}",
                order.getId(),
                symbol,
                order.getStatus(),
                order.getSide(),
                order.getPrice(),
                order.getRemainingQuantity()
        );
        MarketPrice marketPrice = priceCache.get(symbol);

        if (marketPrice == null) {
            log.warn("[MATCH-STOP] marketPrice NULL for symbol={}", symbol);
            return;
        }

        log.info("[MARKET] bestAskPrice={}, bestAskQty={}, bestBidPrice={}, bestBidQty={}",
                marketPrice.getBestAskPrice(),
                marketPrice.getBestAskQuantity(),
                marketPrice.getBestBidPrice(),
                marketPrice.getBestBidQuantity()
        );


        if (order.getStatus() == OrderStatus.FILLED) {
            log.info("[MATCH-STOP] order already FILLED id={}", order.getId());
            return; // bỏ qua order đã filled
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.info("[MATCH-STOP] order already CANCELLED id={}", order.getId());
            return; // bỏ qua order đã filled
        }

        processMatch(order, marketPrice);
    }

    /**
     * Logic so khop dua tren chien luoc "Gia tot nhat" (Market Mirroring)
     */
    private void processMatch(Order order, MarketPrice market) {
        int availableQty = 0;
        BigDecimal matchPrice = BigDecimal.ZERO;
        log.info("[MATCH-RULE] evaluating orderId={}, side={}, price={}",
                order.getId(),
                order.getSide(),
                order.getPrice()
        );


        if (order.getSide() == OrderSide.BUY) {
            log.info("[BUY-CHECK] orderPrice={} >= bestAskPrice={}",
                    order.getPrice(),
                    market.getBestAskPrice()
            );

            // Lenh MUA khop khi: Gia User dat >= Gia thap nhat thi truong dang BAN
            if (order.getPrice().compareTo(market.getBestAskPrice()) >= 0) {

                availableQty = market.getBestAskQuantity();
                matchPrice = market.getBestAskPrice(); // Khớp tại giá thị trường
                log.info("[BUY-MATCH-OK] availableQty={}, matchPrice={}",
                        availableQty,
                        matchPrice
                );

            }
        } else {
            // Lenh BAN khop khi: Gia User dat <= Gia cao nhat thi truong dang MUA
            if (order.getPrice().compareTo(market.getBestBidPrice()) <= 0) {
                availableQty = market.getBestBidQuantity();
                matchPrice = market.getBestBidPrice();
                log.info("[SELL-MATCH-OK] availableQty={}, matchPrice={}",
                        availableQty,
                        matchPrice
                );
            }
        }

        if (availableQty > 0) {
            // So luong khop thuc te = Min(So luong User can, So luong thi truong co)
            int actualMatchQty = Math.min(order.getRemainingQuantity(), availableQty);
            log.info("[EXECUTE-TRADE] actualMatchQty={}, orderRemaining={}, availableQty={}",
                    actualMatchQty,
                    order.getRemainingQuantity(),
                    availableQty
            );
            tradeService.executeTrade(order, matchPrice, actualMatchQty);
        }
    }

}

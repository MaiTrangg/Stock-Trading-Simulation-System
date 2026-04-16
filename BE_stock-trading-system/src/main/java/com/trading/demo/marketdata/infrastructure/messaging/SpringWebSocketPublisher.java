package com.trading.demo.marketdata.infrastructure.messaging;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.PricePushPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringWebSocketPublisher implements PricePushPort {

    private final SimpMessagingTemplate template;

    @Override
    public void push(MarketPrice price) {
        template.convertAndSend("/topic/prices", price);
        template.convertAndSend(
                "/topic/prices/" + price.getSymbol(), price
        );
    }


}

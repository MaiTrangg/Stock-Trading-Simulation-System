package com.trading.demo.trading.application.event;

import com.trading.demo.common.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}

package com.trading.demo.stock.infrastructure.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.trading.demo.stock.application.usecase.InitStockDataUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockDataInitializer implements ApplicationRunner {

    private final InitStockDataUseCase useCase;

    @Override
    public void run(ApplicationArguments args) {
        useCase.initIfEmpty();
    }
}
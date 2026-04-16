package com.trading.demo.stock.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trading.demo.stock.application.usecase.SyncStockDataUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyStockSyncScheduler {

    private final SyncStockDataUseCase useCase;

    @Scheduled(cron = "0 0 6 * * ?")
    public void sync() {
        useCase.syncDaily();
    }
}

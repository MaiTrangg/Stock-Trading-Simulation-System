package com.trading.demo.portfolio.application.service;

import com.trading.demo.portfolio.domain.model.Portfolio;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.infrastructure.persistence.repository.PortfolioJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioPort portfolioPort;

    @Transactional
    public Portfolio ensurePortfolio(UUID userId, UUID stockId) {
        return portfolioPort.findByUserIdAndStockId(userId, stockId)
                .orElseGet(() -> portfolioPort.save(
                        Portfolio.createEmpty(userId, stockId)
                ));
    }
}

package com.trading.demo.portfolio.application.usecase.portfolio;

import com.trading.demo.portfolio.domain.model.Portfolio;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.presentation.dto.response.PortfolioSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPortfolioSummaryUseCase {

    private final PortfolioPort portfolioPort;

    public PortfolioSummaryResponse execute(UUID userId) {

        List<Portfolio> portfolios = portfolioPort.findByUserId(userId);

        BigDecimal totalValue = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (Portfolio p : portfolios) {
            totalQuantity += p.getQuantity();

            // demo: dung avg_price * quantity
            BigDecimal value = p.getAvgPrice()
                    .multiply(BigDecimal.valueOf(p.getQuantity()));

            totalValue = totalValue.add(value);
        }

        return PortfolioSummaryResponse.builder()
                .totalValue(totalValue)
                .totalQuantity(totalQuantity)
                .build();
    }
}

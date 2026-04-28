package com.trading.demo.portfolio.application.usecase.portfolio;

import com.trading.demo.portfolio.domain.model.Portfolio;
import com.trading.demo.portfolio.domain.port.PortfolioPort;
import com.trading.demo.portfolio.presentation.dto.response.HoldingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetHoldingsUseCase {

    private final PortfolioPort portfolioPort;

    public List<HoldingResponse> execute(UUID userId) {

        List<Portfolio> portfolios = portfolioPort.findByUserId(userId);

        return portfolios.stream()
                .map(p -> HoldingResponse.builder()
                        .stockId(p.getStockId())
                        .totalQuantity(p.getQuantity())
                        .lockedQuantity(p.getLockedQuantity())
                        .availableQuantity(
                                p.getQuantity() - p.getLockedQuantity()
                        )
                        .avgPrice(p.getAvgPrice())
                        .build())
                .toList();
    }
}

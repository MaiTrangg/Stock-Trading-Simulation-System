package com.trading.demo.trading.application.usecase.trade;

import com.trading.demo.trading.application.dto.response.TradeDetailResponse;
import com.trading.demo.trading.application.dto.response.TradeResponse;
import com.trading.demo.trading.application.mapper.TradeResponseMapper;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.domain.port.TradePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTradesUseCase {

    private final TradePort tradePort;
    private final TradeResponseMapper tradeResponseMapper;

    public List<TradeResponse> execute(
            UUID userId,
            UUID stockId,
            OrderSide side,
            LocalDateTime from,
            LocalDateTime to
    ) {

        List<Trade> trades = tradePort.findTrades(
                userId,
                stockId,
                side,
                from,
                to
        );

        return trades.stream()
                .map(tradeResponseMapper::toResponse)
                .toList();
    }
}

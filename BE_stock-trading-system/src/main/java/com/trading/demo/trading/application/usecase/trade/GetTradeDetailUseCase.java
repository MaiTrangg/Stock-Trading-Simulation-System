package com.trading.demo.trading.application.usecase.trade;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.trading.application.dto.response.TradeDetailResponse;
import com.trading.demo.trading.application.mapper.TradeResponseMapper;
import com.trading.demo.trading.domain.model.Trade;
import com.trading.demo.trading.domain.port.TradePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTradeDetailUseCase {

    private final TradePort tradePort;
    private final TradeResponseMapper tradeResponseMapper;

    public TradeDetailResponse execute(UUID userId, UUID tradeId) {

        Trade trade = tradePort.findById(tradeId);

        // check quyen User: user phai co quyen mua hoac ban
        if (!trade.getBuyerId().equals(userId)
                && !trade.getSellerId().equals(userId)) {
            throw new AppException(ErrorCode.TRADE_ACCESS_DENIED);
        }

        return tradeResponseMapper.toDetailResponse(trade);
    }
}
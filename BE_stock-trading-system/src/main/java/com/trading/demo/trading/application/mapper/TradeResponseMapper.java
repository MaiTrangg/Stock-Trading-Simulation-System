package com.trading.demo.trading.application.mapper;

import com.trading.demo.trading.application.dto.response.TradeDetailResponse;
import com.trading.demo.trading.application.dto.response.TradeResponse;
import com.trading.demo.trading.domain.model.Trade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface TradeResponseMapper {
    TradeDetailResponse toDetailResponse(Trade trade);

    TradeResponse toResponse(Trade trade);
}

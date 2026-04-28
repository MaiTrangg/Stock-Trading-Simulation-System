package com.trading.demo.trading.application.mapper;

import com.trading.demo.common.mapper.EnumMapper;
import org.mapstruct.Mapper;

import com.trading.demo.trading.application.dto.response.OrderResponse;
import com.trading.demo.trading.application.dto.response.OrderDetailResponse;
import com.trading.demo.trading.application.dto.response.OrderHistoryResponse;
import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.domain.model.OrderHistory;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    OrderResponse toResponse(Order order);

    OrderDetailResponse toDetailResponse(Order order);

    OrderHistoryResponse toHistoryResponse(OrderHistory history);


}

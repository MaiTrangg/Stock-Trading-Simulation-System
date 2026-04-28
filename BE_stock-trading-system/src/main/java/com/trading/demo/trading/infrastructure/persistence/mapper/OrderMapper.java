package com.trading.demo.trading.infrastructure.persistence.mapper;

import com.trading.demo.common.mapper.EnumMapper;
import org.mapstruct.Mapper;

import com.trading.demo.trading.domain.model.Order;
import com.trading.demo.trading.infrastructure.persistence.entity.OrderEntity;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;

@Mapper(componentModel = "spring", uses = EnumMapper.class)
public interface OrderMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order order);

}

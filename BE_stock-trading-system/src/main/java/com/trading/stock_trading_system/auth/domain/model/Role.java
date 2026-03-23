package com.trading.stock_trading_system.auth.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Role {
    private UUID id;
    private String name;
}

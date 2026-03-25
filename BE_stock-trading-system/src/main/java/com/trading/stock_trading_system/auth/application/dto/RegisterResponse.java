package com.trading.stock_trading_system.auth.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterResponse {
    private UUID id;
    private String username;
    private String email;
    private String status;
    private Boolean isDelete;
}

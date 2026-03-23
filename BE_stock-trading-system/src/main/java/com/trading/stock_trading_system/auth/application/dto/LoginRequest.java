package com.trading.stock_trading_system.auth.application.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

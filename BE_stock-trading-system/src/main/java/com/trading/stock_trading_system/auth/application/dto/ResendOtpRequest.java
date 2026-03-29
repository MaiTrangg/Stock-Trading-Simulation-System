package com.trading.stock_trading_system.auth.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendOtpRequest {
    private String email;
}

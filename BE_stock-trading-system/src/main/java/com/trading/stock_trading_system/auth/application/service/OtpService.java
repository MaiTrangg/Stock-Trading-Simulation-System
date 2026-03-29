package com.trading.stock_trading_system.auth.application.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}

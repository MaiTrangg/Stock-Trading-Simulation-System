package com.trading.stock_trading_system.auth.application.service;

public interface EmailService {
    void sendOtp(String toEmail, String otp);
}

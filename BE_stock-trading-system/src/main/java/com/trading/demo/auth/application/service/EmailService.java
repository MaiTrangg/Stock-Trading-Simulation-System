package com.trading.demo.auth.application.service;

public interface EmailService {
    void sendOtp(String toEmail, String otp);
}

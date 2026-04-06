package com.trading.demo.auth.application.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpGenerator {

    public String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}

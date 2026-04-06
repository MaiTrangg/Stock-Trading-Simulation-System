package com.trading.demo.auth.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    private String email;
    private String otp;
}

package com.trading.demo.auth.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.auth.application.constant.AuthMessage;
import com.trading.demo.auth.application.dto.request.LoginRequest;
import com.trading.demo.auth.application.dto.request.RegisterRequest;
import com.trading.demo.auth.application.dto.request.ResendOtpRequest;
import com.trading.demo.auth.application.dto.request.VerifyOtpRequest;
import com.trading.demo.auth.application.dto.response.AuthResponse;
import com.trading.demo.auth.application.dto.response.RegisterResponse;
import com.trading.demo.auth.application.usecase.LoginUseCase;
import com.trading.demo.auth.application.usecase.LogoutUseCase;
import com.trading.demo.auth.application.usecase.RefreshTokenUseCase;
import com.trading.demo.auth.application.usecase.RegisterUseCase;
import com.trading.demo.auth.application.usecase.ResendOtpUseCase;
import com.trading.demo.auth.application.usecase.VerifyOtpUseCase;
import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final VerifyOtpUseCase verifyOtpUseCase;
    private final ResendOtpUseCase resendOtpUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return new ApiResponse<RegisterResponse>()
                .success(AuthMessage.REGISTER_SUCCESS, registerUseCase.execute(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ApiResponse<AuthResponse>()
                .success(AuthMessage.LOGIN_SUCCESS, loginUseCase.execute(request));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestParam String refreshToken) {
        logoutUseCase.execute(refreshToken);
        return new ApiResponse<String>().success(AuthMessage.LOGOUT_SUCCESS, null);
    }

    @SuppressWarnings("checkstyle:LineLength")
    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshToken refreshToken) {
        return new ApiResponse<AuthResponse>()
                .success(
                        AuthMessage.REFRESH_TOKEN_SUCCESS,
                        refreshTokenUseCase.execute(refreshToken.getToken()));
    }

    @PostMapping("/verify-otp")
    public ApiResponse<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        verifyOtpUseCase.execute(request.getEmail(), request.getOtp());
        return new ApiResponse<String>().success(AuthMessage.VERIFY_OTP_SUCCESS, null);
    }

    @PostMapping("/resend-otp")
    public ApiResponse<String> resendOtp(@RequestBody ResendOtpRequest request) {
        resendOtpUseCase.execute(request.getEmail());
        return new ApiResponse<String>().success(AuthMessage.RESEND_OTP_SUCCESS, null);
    }
}

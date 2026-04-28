package com.trading.demo.auth.presentation.controller;

import com.trading.demo.auth.application.dto.request.*;
import com.trading.demo.auth.application.usecase.ForgotPasswordUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.auth.application.constant.AuthMessage;
import com.trading.demo.auth.application.dto.response.AuthResponse;
import com.trading.demo.auth.application.dto.response.RegisterResponse;
import com.trading.demo.auth.application.usecase.LoginUseCase;
import com.trading.demo.auth.application.usecase.LogoutUseCase;
import com.trading.demo.auth.application.usecase.RefreshTokenUseCase;
import com.trading.demo.auth.application.usecase.RegisterUseCase;
import com.trading.demo.auth.application.usecase.ResendOtpUseCase;
import com.trading.demo.auth.application.usecase.ResetPasswordUseCase;
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
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ApiResponse.success(registerUseCase.execute(request), AuthMessage.REGISTER_SUCCESS);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.success(loginUseCase.execute(request), AuthMessage.LOGIN_SUCCESS);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestParam String refreshToken) {
        logoutUseCase.execute(refreshToken);
        return ApiResponse.success(null, AuthMessage.LOGOUT_SUCCESS);
    }


    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest req) {
        return ApiResponse.success(
                refreshTokenUseCase.execute(req.getToken()),
                AuthMessage.REFRESH_TOKEN_SUCCESS);
    }

    @PostMapping("/verify-otp")
    public ApiResponse<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        verifyOtpUseCase.execute(request.getEmail(), request.getOtp());
        return ApiResponse.success(null, AuthMessage.VERIFY_OTP_SUCCESS);
    }

    @PostMapping("/resend-otp")
    public ApiResponse<String> resendOtp(@RequestBody ResendOtpRequest request) {
        resendOtpUseCase.execute(request.getEmail());
        return ApiResponse.success(null, AuthMessage.RESEND_OTP_SUCCESS);
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody ResendOtpRequest request) {
        forgotPasswordUseCase.execute(request.getEmail());
        return ApiResponse.success(null, AuthMessage.SEND_OTP_SUCCESS);
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request.getEmail(), request.getToken(), request.getNewPassword());
        return ApiResponse.success(null, AuthMessage.RESET_PASSWORD_SUCCESS);
    }
}

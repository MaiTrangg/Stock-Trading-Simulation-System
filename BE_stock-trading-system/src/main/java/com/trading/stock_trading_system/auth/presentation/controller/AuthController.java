package com.trading.stock_trading_system.auth.presentation.controller;

import com.trading.stock_trading_system.auth.application.dto.*;
import com.trading.stock_trading_system.auth.application.usecase.*;
import com.trading.stock_trading_system.auth.domain.model.RefreshToken;
import com.trading.stock_trading_system.common.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final VerifyOtpUseCase verifyOtpUseCase;
    private final  ResendOtpUseCase resendOtpUseCase;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(registerUseCase.execute(request));
        return apiResponse ;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(loginUseCase.execute(request));
        return apiResponse;
    }

    @PostMapping("/logout")
    public void logout(@RequestParam String refreshToken) {
        logoutUseCase.execute(refreshToken);
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshToken refreshToken){
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        RefreshTokenUseCase refreshTokenUseCase = new RefreshTokenUseCase();
        apiResponse.setData(refreshTokenUseCase.execute(refreshToken.getToken()));
        return apiResponse;
    }

    @PostMapping("/verify-otp")
    public ApiResponse<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        verifyOtpUseCase.execute(
                request.getEmail(),
                request.getOtp()
        );
        return apiResponse.success("Verify OTP success");
    }
    @PostMapping("/resend-otp")
    public ApiResponse<String> resendOtp(@RequestBody ResendOtpRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        resendOtpUseCase.execute(request.getEmail());
        return apiResponse.success("Resend OTP success");
    }
}


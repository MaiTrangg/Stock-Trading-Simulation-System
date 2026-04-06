package com.trading.demo.auth.presentation.controller;

import com.trading.demo.auth.application.dto.*;
import com.trading.demo.auth.application.usecase.*;
import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.common.exception.ApiResponse;
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
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

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

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody ResendOtpRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        forgotPasswordUseCase.execute(request.getEmail());
        return apiResponse.success("Sent OTP success");
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        resetPasswordUseCase.execute(request.getEmail(), request.getToken(), request.getNewPassword());
        return apiResponse.success("Reset password success");
    }
}


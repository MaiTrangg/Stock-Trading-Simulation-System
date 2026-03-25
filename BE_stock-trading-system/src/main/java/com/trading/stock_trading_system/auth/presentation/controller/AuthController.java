package com.trading.stock_trading_system.auth.presentation.controller;

import com.trading.stock_trading_system.auth.application.dto.AuthResponse;
import com.trading.stock_trading_system.auth.application.dto.LoginRequest;
import com.trading.stock_trading_system.auth.application.dto.RegisterRequest;
import com.trading.stock_trading_system.auth.application.dto.RegisterResponse;
import com.trading.stock_trading_system.auth.application.usecase.LoginUseCase;
import com.trading.stock_trading_system.auth.application.usecase.LogoutUseCase;
import com.trading.stock_trading_system.auth.application.usecase.RegisterUseCase;
import com.trading.stock_trading_system.common.exception.ApiResponse;
import com.trading.stock_trading_system.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;

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
}

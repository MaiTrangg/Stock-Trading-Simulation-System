package com.trading.stock_trading_system.auth.presentation.controller;

import com.trading.stock_trading_system.auth.application.dto.AuthResponse;
import com.trading.stock_trading_system.auth.application.dto.LoginRequest;
import com.trading.stock_trading_system.auth.application.dto.RegisterRequest;
import com.trading.stock_trading_system.auth.application.usecase.LoginUseCase;
import com.trading.stock_trading_system.auth.application.usecase.LogoutUseCase;
import com.trading.stock_trading_system.auth.application.usecase.RegisterUseCase;
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
    public void register(@RequestBody RegisterRequest request) {
        registerUseCase.execute(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestParam String refreshToken) {
        logoutUseCase.execute(refreshToken);
    }
}

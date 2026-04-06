package com.trading.demo.auth.infrastructure.security;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserPrincipal {

    private UUID userId;
    private List<String> roles;
}

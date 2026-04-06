package com.trading.demo.auth.infrastructure.security.constants;

import java.security.Key;

import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
    public static final String SECRET = "o4U/RWi6ILdctFlPAvJK2Iqc9Bxn+dfvHjdNjOVApu0=";
    public static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    public static final long EXPIRATION = 1000 * 60 * 60; // 1h
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH = "Authorization";
    public static final String CLAIM_ROLES = "roles";
    public static final String ROLE_PREFIX = "ROLE_";
    // API
    public static final String AUTH_LOGIN = "/api/auth/login";
    public static final String AUTH_REGISTER = "/api/auth/register";
    public static final String AUTH_VERIFY_OTP = "/api/auth/verify-otp";
    public static final String AUTH_RESEND_OTP = "/api/auth/resend-otp";
    public static final String JWT_EXPIRED = "JWT expired";
    public static final String JWT_INVALID_SIGNATURE = "Invalid JWT signature";
    public static final String JWT_AUTH_ERROR = "JWT authentication error";
}

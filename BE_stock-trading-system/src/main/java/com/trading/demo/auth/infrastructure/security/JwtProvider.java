package com.trading.demo.auth.infrastructure.security;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.trading.demo.auth.infrastructure.security.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtProvider {

    public String generateToken(UUID userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim(SecurityConstants.CLAIM_ROLES, roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION))
                .signWith(SecurityConstants.KEY)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

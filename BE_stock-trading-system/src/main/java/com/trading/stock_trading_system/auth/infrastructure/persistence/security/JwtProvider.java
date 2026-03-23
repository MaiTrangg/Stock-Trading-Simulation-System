package com.trading.stock_trading_system.auth.infrastructure.persistence.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final String SECRET = "o4U/RWi6ILdctFlPAvJK2Iqc9Bxn+dfvHjdNjOVApu0=";
    private final long EXPIRATION = 1000 * 60 * 60; // 1h

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public UUID getUserId(String token) {
        return UUID.fromString(
                Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

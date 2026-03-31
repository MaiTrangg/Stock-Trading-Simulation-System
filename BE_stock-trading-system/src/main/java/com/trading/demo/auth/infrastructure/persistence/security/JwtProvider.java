package com.trading.demo.auth.infrastructure.persistence.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtProvider {

    private final String SECRET = "o4U/RWi6ILdctFlPAvJK2Iqc9Bxn+dfvHjdNjOVApu0=";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION = 1000 * 60 * 60; // 1h

    public String generateToken(UUID userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public UUID getUserId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    public String generateRefreshToken(){
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

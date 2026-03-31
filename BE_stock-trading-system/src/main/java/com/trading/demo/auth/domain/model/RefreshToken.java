package com.trading.demo.auth.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RefreshToken {

    private UUID id;
    private UUID userId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean revoked;

    // FACTORY METHOD
    public static RefreshToken create(UUID userId, String tokenValue) {

        RefreshToken token = new RefreshToken();
        token.setId(UUID.randomUUID());
        token.setUserId(userId);
        token.setToken(tokenValue);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(2));
        token.setRevoked(false);

        return token;
    }

    public boolean isValid() {
        return !revoked && expiresAt.isAfter(LocalDateTime.now());
    }
}

package com.trading.demo.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.auth.domain.enums.OtpStatus;
import com.trading.demo.auth.domain.enums.OtpType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {

    private UUID id;
    private UUID userId;
    private String token;

    private OtpType type;
    private OtpStatus status;

    private LocalDateTime expiresAt;

    private int verifyAttempts;
    private int resendCount;
    private LocalDateTime lastSentAt;

    // FACTORY METHOD
    public static EmailVerification create(UUID userId, String token) {
        EmailVerification ev = new EmailVerification();
        ev.id = UUID.randomUUID();
        ev.userId = userId;
        ev.token = token;
        ev.type = OtpType.REGISTER;
        ev.status = OtpStatus.ACTIVE;
        ev.expiresAt = LocalDateTime.now().plusMinutes(5);
        ev.verifyAttempts = 0;
        ev.resendCount = 0;
        ev.lastSentAt = LocalDateTime.now();
        return ev;
    }

    // BUSINESS METHODS
    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean canVerify(int maxAttempt) {
        return verifyAttempts < maxAttempt && status == OtpStatus.ACTIVE;
    }

    public boolean isCooldown() {
        return lastSentAt != null && lastSentAt.isAfter(LocalDateTime.now().minusSeconds(30));
    }
}

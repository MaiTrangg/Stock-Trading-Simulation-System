package com.trading.demo.auth.domain.model;

import com.trading.demo.auth.domain.enums.OtpStatus;
import com.trading.demo.auth.domain.enums.OtpType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
        System.out.println("expiresAt: "+expiresAt);
        return expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean canVerify(int maxAttempt) {
        System.out.println("verifyAttempts: "+verifyAttempts);
        System.out.println("maxAttempt: "+ maxAttempt);
        System.out.println("status: "+ status);
        return verifyAttempts < maxAttempt && status == OtpStatus.ACTIVE;
    }

    public boolean isCooldown() {
        return lastSentAt != null &&
                lastSentAt.isAfter(LocalDateTime.now().minusSeconds(30));
    }
}

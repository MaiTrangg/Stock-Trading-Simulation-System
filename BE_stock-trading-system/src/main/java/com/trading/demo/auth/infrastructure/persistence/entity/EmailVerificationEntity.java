package com.trading.demo.auth.infrastructure.persistence.entity;

import com.trading.demo.auth.domain.enums.OtpStatus;
import com.trading.demo.auth.domain.enums.OtpType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_verifications")
public class EmailVerificationEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 255)
    private String token; // hashed OTP


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType type; //  EMAIL_VERIFICATION, RESET_PASSWORD,...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpStatus status; // ACTIVE, EXPIRED, VERIFIED

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;



    @Column(name = "verify_attempts", nullable = false)
    private int verifyAttempts = 0;

    @Column(name = "resend_count", nullable = false)
    private int resendCount = 0;

    @Column(name = "last_sent_at", nullable = false, updatable = false)
    private LocalDateTime lastSentAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "created_by")
    private UUID createdBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;


    @Version
    @Column(nullable = false)
    private long version;

}

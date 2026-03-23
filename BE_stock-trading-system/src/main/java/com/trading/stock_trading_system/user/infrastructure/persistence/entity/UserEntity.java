package com.trading.stock_trading_system.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID id;

    private String username;
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    private String status;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Version
    @Column(name = "version")
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

//    @PrePersist
//    public void prePersist() {
//        if (createdAt == null) {
//            createdAt = LocalDateTime.now();
//        }
//    }
}

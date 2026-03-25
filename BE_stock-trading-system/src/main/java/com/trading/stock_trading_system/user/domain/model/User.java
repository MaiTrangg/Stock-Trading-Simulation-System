package com.trading.stock_trading_system.user.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class User {
        private UUID id;
        private String username;
        private String email;
        private String passwordHash;
        private String status;

        private Boolean isDelete;
        private Long version;
        private LocalDateTime createdAt;

        // ? FACTORY METHOD
        public static User create(String username, String email, String encodedPassword) {

                User user = new User();
                user.setId(UUID.randomUUID());
                user.setUsername(username);
                user.setEmail(email);
                user.setPasswordHash(encodedPassword);
                user.setStatus("ACTIVE");
                user.setIsDelete(false);

                return user;
        }
        public boolean isActive() {
            return "ACTIVE".equals(this.status) && !Boolean.TRUE.equals(this.isDelete);
        }

}

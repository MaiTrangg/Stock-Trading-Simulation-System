package com.trading.demo.user.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trading.demo.auth.domain.enums.AuthProvider;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.user.domain.enums.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private String status;
    private String provider;
    private String providerId;

    private Boolean isDelete;
    private Long version;
    private LocalDateTime createdAt;

    //  FACTORY METHOD
    public static User create(String username, String email, String encodedPassword) {

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(encodedPassword);
        user.setProvider(AuthProvider.LOCAL.name());
        user.setStatus(UserStatus.INACTIVE.name());
        user.setIsDelete(false);

        return user;
    }

    public static User createGoogle(String username, String email, String providerId) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.username = username;
        user.email = email;
        user.passwordHash = null;
        user.provider = AuthProvider.GOOGLE.name();
        user.providerId = providerId;
        user.status = UserStatus.ACTIVE.name();
        user.setIsDelete(false);
        return user;
    }

    public boolean isLocal() {
        return AuthProvider.LOCAL.name().equals(this.provider);
    }

    public void updateProfile(String username) {
        if (username == null || username.isBlank()) {
            throw new AppException(ErrorCode.USER_INACTIVE);
        }
        this.username = username;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.name().equals(this.status) && !Boolean.TRUE.equals(this.isDelete);
    }

    public void changePassword(String newEncodedPassword) {
        if (newEncodedPassword == null || newEncodedPassword.isBlank()) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        this.passwordHash = newEncodedPassword;
    }

}

package com.trading.demo.user.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String userName;
    private String email;
    private String status;
    private Boolean isDelete;
    private LocalDateTime createdAt;

    public static UserResponse create(
            UUID id,
            String userName,
            String email,
            String status,
            Boolean isDelete,
            LocalDateTime createdAt) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setUserName(userName);
        userResponse.setEmail(email);
        userResponse.setStatus(status);
        userResponse.setIsDelete(isDelete);
        userResponse.setCreatedAt(createdAt);
        return userResponse;
    }
}

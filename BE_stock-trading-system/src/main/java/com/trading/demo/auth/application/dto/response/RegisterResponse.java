package com.trading.demo.auth.application.dto.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private UUID id;
    private String username;
    private String email;
    private String status;
    private Boolean isDelete;
}

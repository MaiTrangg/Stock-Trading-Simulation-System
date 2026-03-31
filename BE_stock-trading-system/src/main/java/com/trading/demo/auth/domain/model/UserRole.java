package com.trading.demo.auth.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRole {
    private UUID id;
    private UUID userId;
    private UUID roleId;
}

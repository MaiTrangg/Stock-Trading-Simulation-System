package com.trading.demo.auth.domain.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private UUID id;
    private String name;
}

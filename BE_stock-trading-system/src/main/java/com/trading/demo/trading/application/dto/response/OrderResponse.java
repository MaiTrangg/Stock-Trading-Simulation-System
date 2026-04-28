package com.trading.demo.trading.application.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderResponse {
    private UUID id;
    private String orderNo;
    private String status;
}

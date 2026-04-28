package com.trading.demo.trading.application.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderHistoryResponse {
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}

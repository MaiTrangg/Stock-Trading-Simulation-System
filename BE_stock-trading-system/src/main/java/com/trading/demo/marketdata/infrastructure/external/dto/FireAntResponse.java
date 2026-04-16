package com.trading.demo.marketdata.infrastructure.external.dto;

import java.util.Map;

import lombok.Data;


@Data
public class FireAntResponse {
    private Map<String, Object> data;
}

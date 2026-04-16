package com.trading.demo.marketdata.infrastructure.external.mcp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class McpClient {

    private final RestTemplate restTemplate;

    @Value("${spring.mcp.url}")
    private String url;

    public Map<String, Object> call(String name, Map<String, Object> args) {

        Map<String, Object> body = Map.of(
                "name", name,
                "arguments", args
        );

        return restTemplate.postForObject(url, body, Map.class);
    }
}


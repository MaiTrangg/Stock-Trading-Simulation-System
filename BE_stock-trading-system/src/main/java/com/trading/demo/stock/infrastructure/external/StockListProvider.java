package com.trading.demo.stock.infrastructure.external;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.trading.demo.marketdata.infrastructure.external.mcp.McpClient;
import com.trading.demo.stock.domain.enums.StockStatus;
import com.trading.demo.stock.domain.model.Stock;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockListProvider {

    private final McpClient client;

    public List<Stock> fetchAll() {

        Map response = client.call(
                "list_vn_stocks",
                Map.of("exchange", "HOSE")
        );

        List<Map> stocks = (List<Map>) response.get("stocks");

        return stocks.stream()
                .map(this::map)
                .toList();
    }

    private Stock map(Map data) {
        return Stock.builder()
                .id(UUID.randomUUID())
                .symbol(data.get("symbol").toString())
                .companyName(data.get("name").toString())
                .exchange(data.get("exchange").toString())
                .status(StockStatus.ACTIVE.name())
                .build();
    }
}

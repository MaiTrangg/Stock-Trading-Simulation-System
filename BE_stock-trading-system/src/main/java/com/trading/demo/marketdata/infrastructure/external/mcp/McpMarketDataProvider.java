package com.trading.demo.marketdata.infrastructure.external.mcp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.util.NumberUtil;
import com.trading.demo.marketdata.application.constant.FireantFields;
import com.trading.demo.marketdata.application.constant.MarketDataConstant;
import com.trading.demo.marketdata.domain.model.MarketPrice;
import com.trading.demo.marketdata.domain.port.out.MarketDataProviderPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class McpMarketDataProvider implements MarketDataProviderPort {

    private final McpClient client;

    @Override
    public List<MarketPrice> getLatestPrices(List<String> symbols) {

        try {
            String symbolStr = String.join(",", symbols);

            Map<String, Object> response = client.call(
                    MarketDataConstant.API_GET_PRICE,
                    Map.of(FireantFields.SYMBOL, symbolStr)
            );

            List<Map<String, Object>> results =
                    (List<Map<String, Object>>) response.get(FireantFields.RESULTS);

            if (results == null || results.isEmpty()) {
                log.warn("NO DATA RETURNED: {}", symbols);
                return List.of();
            }

            return results.stream()
                    .map(this::mapItemToMarketPrice)
                    .toList();

        } catch (Exception e) {
            log.error("FETCH BATCH FAILED: {}", symbols, e);
            throw new AppException(ErrorCode.MARKET_PRICE_FETCH_FAILED);
        }
    }

    private MarketPrice mapItemToMarketPrice(Map<String, Object> item) {

        Map<String, Object> data =
                (Map<String, Object>) item.get(FireantFields.DATA);

        // get object bid and ask to get price1
        Map<String, Object> bidMap = (Map<String, Object>) data.get("bid");
        Map<String, Object> askMap = (Map<String, Object>) data.get("ask");

        String observedAt = (String) data.get(FireantFields.OBSERVED_AT);

        return MarketPrice.builder()
                .symbol((String) data.get(FireantFields.SYMBOL))

                .open(new BigDecimal(data.get(FireantFields.OPEN).toString()))
                .high(new BigDecimal(data.get(FireantFields.HIGH).toString()))
                .low(new BigDecimal(data.get(FireantFields.LOW).toString()))
                .close(new BigDecimal(data.get(FireantFields.CLOSE).toString()))

                // correct field
                .volume(Long.valueOf(data.get(FireantFields.VOLUME).toString()))

                .ceilingPrice(NumberUtil.toBigDecimal(data.get("ceilingPrice")))
                .floorPrice(NumberUtil.toBigDecimal(data.get("floorPrice")))

                // get price and quantity bestBidPrice
                .bestBidPrice(NumberUtil.toBigDecimal(bidMap.get("price1")))
                .bestBidQuantity(NumberUtil.toInt(bidMap.get("quantity1")))

                // get price and quantity bestAskPrice
                .bestAskPrice(NumberUtil.toBigDecimal(askMap.get("price1")))
                .bestAskQuantity(NumberUtil.toInt(askMap.get("quantity1")))

                .timestamp(Instant.parse(observedAt).atZone(ZoneId.systemDefault()).toLocalDateTime())

                .build();
    }


}



package com.trading.demo.trading.domain.service;

import com.trading.demo.trading.domain.model.Order;

import java.math.BigDecimal;

public interface MatchingService {
    /**
     * Khop lenh ngay lap tuc khi vua dat lenh
     */
    void matchImmediate(Order order, String symbol);
}

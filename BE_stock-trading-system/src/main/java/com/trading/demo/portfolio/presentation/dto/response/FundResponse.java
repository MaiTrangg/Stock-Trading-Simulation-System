package com.trading.demo.portfolio.presentation.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundResponse {
    private BigDecimal balance;
    private BigDecimal available;
    private BigDecimal reserved;
}

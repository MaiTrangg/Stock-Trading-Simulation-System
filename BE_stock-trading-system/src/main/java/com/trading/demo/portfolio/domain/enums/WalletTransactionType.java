package com.trading.demo.portfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WalletTransactionType {
    RESERVE,//("Phong toa tien khi dat lenh mua"),
    RELEASE,//("Tru tien da phong toa khi khop lenh mua"),
    REFUND,//("Hoan tien khi huy lenh mua"),
    TRADE_SELL,//("Cong tien khi khop lenh ban")
    CANCEL_REFUND,
    DEPOSIT;

}

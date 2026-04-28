package com.trading.demo.portfolio.domain.port;

import com.trading.demo.portfolio.domain.enums.WalletTransactionType;
import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.domain.model.WalletTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletPort {
    /**
     * Phong toa tien khi dat lenh MUA.
     * (Giam available_balance, tang reserved_balance. Tong balance khong doi)
     */
    void reserve(UUID userId, BigDecimal amount, UUID referenceId);

    /**
     * Giai phong va tru tien thuc te khi khop lenh MUA thanh cong.
     * (Tru balance va tru reserved_balance)
     */
    void release(UUID userId, BigDecimal amount, UUID referenceId);

    /**
     * Cong tien vao tai khoan khi khop lenh BAN thanh cong.
     * (Tang balance va tang available_balance)
     */
    void addBalance(UUID userId, BigDecimal amount, UUID referenceId);

    /**
     * Hoan tien khi huy lenh MUA.
     * (Giam reserved_balance, tang lai available_balance)
     */
    void refund(UUID userId, BigDecimal amount, UUID referenceId);

    Optional<Fund> findByUserIdAndIsDeleteFalse(UUID userId);

    Fund save(Fund fund);


    void deposit(UUID userId, BigDecimal amount, UUID referenceId);

}
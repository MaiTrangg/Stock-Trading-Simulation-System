package com.trading.demo.portfolio.application.service;

import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.portfolio.infrastructure.persistence.repository.WalletJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletPort walletRepo;

    @Transactional
    public Fund ensureFund(UUID userId) {
        return walletRepo.findByUserIdAndIsDeleteFalse(userId)
                .orElseGet(() -> walletRepo.save(Fund.createEmpty(userId)));
    }
}

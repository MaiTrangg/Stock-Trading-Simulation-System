package com.trading.demo.portfolio.application.mapper;

import com.trading.demo.portfolio.domain.model.WalletTransaction;
import com.trading.demo.portfolio.presentation.dto.response.WalletTransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface WalletTransactionResponseMapper {
    WalletTransactionResponse toResponse(WalletTransaction w);
}

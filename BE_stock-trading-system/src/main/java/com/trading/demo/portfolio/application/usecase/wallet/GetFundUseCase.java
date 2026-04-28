package com.trading.demo.portfolio.application.usecase.wallet;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.portfolio.application.mapper.FundResponseMapper;
import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.domain.port.WalletPort;
import com.trading.demo.portfolio.presentation.dto.response.FundResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class GetFundUseCase {

    private final WalletPort walletPort;
    private final FundResponseMapper fundResponseMapper;

    public FundResponse execute(UUID userId) {

        if (userId == null) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Fund fund = walletPort.findByUserIdAndIsDeleteFalse(userId)
                .orElseThrow(() -> new AppException(ErrorCode.FUND_NOT_FOUND));

        if (fund == null) {
            throw new AppException(ErrorCode.FUND_NOT_FOUND);
        }

        return fundResponseMapper.toResponse(fund);
    }
}

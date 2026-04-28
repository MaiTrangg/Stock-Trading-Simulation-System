package com.trading.demo.portfolio.application.mapper;

import org.mapstruct.Mapper;

import com.trading.demo.portfolio.domain.model.Fund;
import com.trading.demo.portfolio.presentation.dto.response.FundResponse;

@Mapper(componentModel = "Spring")
public interface FundResponseMapper {
    FundResponse toResponse(Fund fund);
}

package com.trading.demo.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.common.enums.ErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(errorCode.getStatus().value());
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setSuccess(false);
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}

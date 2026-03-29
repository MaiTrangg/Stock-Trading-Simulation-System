package com.trading.stock_trading_system.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(RuntimeException.class)
//    ResponseEntity<String> handlingRunTimeException(RuntimeException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(errorCode.getStatus().value());
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setSuccess(false);
        return ResponseEntity.badRequest().body(apiResponse);

    }


}

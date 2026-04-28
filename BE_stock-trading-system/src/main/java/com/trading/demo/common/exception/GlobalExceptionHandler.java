package com.trading.demo.common.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.common.enums.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        
        // Null safety check
        if (errorCode == null) {
            log.warn("AppException thrown with null ErrorCode: {}", e.getMessage());
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
        
        log.warn("Business exception: {} - {}", errorCode.getCode(), e.getMessage());
        return buildResponse(errorCode, null);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        log.warn("Validation failed: {}", message);
        
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .data(null)
                .message(message)
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .code("VAL001")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiResponse<?>> handlingTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("Invalid request parameter: {}", e.getName());
        
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .data(null)
                .message("Invalid request parameter")
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .code("VAL002")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiResponse<?>> handlingIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Invalid argument: {}", e.getMessage());
        
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .data(null)
                .message("Invalid request")
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .code("VAL003")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.badRequest().body(apiResponse);
    }
    
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<?>> handlingGenericException(Exception e) {
        log.error("Unexpected system error", e);
        
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return buildResponse(errorCode, null);
    }
    
    /**
     * Helper method to build consistent error response
     */
    private ResponseEntity<ApiResponse<?>> buildResponse(ErrorCode errorCode, Object data) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .data(data)
                .message(errorCode.getMessage())
                .success(false)
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}

package com.trading.stock_trading_system.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    //USER
    EMAIL_ALREADY_EXISTSCODE("U001","Email existed",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U002", "User not found", HttpStatus.NOT_FOUND),

    //ROLE
    ROLE_NOT_FOUND("R001","Role not found",HttpStatus.NOT_FOUND),

    //AUTH
    TOKEN_NOT_FOUND("A001", "Refresh token not found", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("A002", "Invalid token", HttpStatus.UNAUTHORIZED),
    USER_INACTIVE("A003", "User is not active", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS("A004", "Invalid password", HttpStatus.BAD_REQUEST),
    ;
    private String code;
    private String message;
    private HttpStatus status;
}

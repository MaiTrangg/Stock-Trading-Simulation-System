package com.trading.demo.common.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    // USER
    EMAIL_ALREADY_EXISTSCODE("U001", "Email existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U002", "User not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID("U003", "Username cannot be empty", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED("U004", "Username already exists", HttpStatus.CONFLICT),
    OLD_PASSWORD_INCORRECT("U005", "Old password is incorrect", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME("U006", "New password must be different from old password", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("U007", "Password is invalid", HttpStatus.BAD_REQUEST),

    // ROLE
    ROLE_NOT_FOUND("R001", "Role not found", HttpStatus.NOT_FOUND),

    // AUTH
    TOKEN_NOT_FOUND("A001", "Refresh token not found", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("A002", "Invalid token", HttpStatus.UNAUTHORIZED),
    USER_INACTIVE("A003", "User is not active", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS("A004", "Invalid password", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED("A005", "OTP has expired", HttpStatus.BAD_REQUEST),
    OTP_INVALID("A006", "OTP is invalid", HttpStatus.BAD_REQUEST),
    OTP_TOO_MANY_ATTEMPTS("A007", "Too many attempts, OTP is blocked", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED("A008", "Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR),
    EmailVerification_NOT_FOUND("A009", "EmailVerification not found", HttpStatus.NOT_FOUND),
    OTP_COOLDOWN("A010", "OTP is still in cooldown period", HttpStatus.TOO_MANY_REQUESTS),
    OTP_ALREADY_EXISTS("A011", "There is already an active OTP", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("A012", "Unauthorized", HttpStatus.UNAUTHORIZED),

    // ===== MARKET =====
    MARKET_API_RESPONSE_NULL("M001", "Market API response is null", HttpStatus.BAD_GATEWAY),

    MARKET_API_RATE_LIMIT("M002", "Market API rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS),

    MARKET_API_INVALID_RESPONSE("M003", "Market API invalid response", HttpStatus.BAD_GATEWAY),

    MARKET_API_DATE_PARSE_ERROR("M004", "Failed to parse date from Market API", HttpStatus.INTERNAL_SERVER_ERROR),

    MARKET_API_NUMBER_PARSE_ERROR("M005", "Failed to parse number from Market API", HttpStatus.INTERNAL_SERVER_ERROR),

    MARKET_API_HTTP_ERROR("M006", "Market API HTTP call failed", HttpStatus.BAD_GATEWAY),

    MARKET_API_UNKNOWN_ERROR("M007", "Unknown Market API error", HttpStatus.INTERNAL_SERVER_ERROR),
    MARKET_PRICE_FETCH_FAILED("M008", "Failed to fetch market prices from provider", HttpStatus.BAD_GATEWAY),

    //=== TRADING ====
    ORDER_NOT_FOUND("T001", "Order not found", HttpStatus.NOT_FOUND),

    INVALID_ORDER_SIDE("T002", "Invalid order side", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_TYPE("T003", "Invalid order type", HttpStatus.BAD_REQUEST),

    INSUFFICIENT_BALANCE("T004", "Insufficient balance", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK("T005", "Insufficient stock quantity", HttpStatus.BAD_REQUEST),

    ORDER_ALREADY_FILLED("T006", "Order already filled", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CANCELLED("T007", "Order already cancelled", HttpStatus.BAD_REQUEST),

    INVALID_ORDER_STATUS("T008", "Invalid order status", HttpStatus.BAD_REQUEST),

    MATCHING_FAILED("T009", "Order matching failed", HttpStatus.INTERNAL_SERVER_ERROR),

    TRADE_EXECUTION_FAILED("T010", "Trade execution failed", HttpStatus.INTERNAL_SERVER_ERROR),

    PRICE_NOT_AVAILABLE("T011", "Market price not available", HttpStatus.BAD_GATEWAY),

    ORDER_CREATION_FAILED("T012", "Failed to create order", HttpStatus.INTERNAL_SERVER_ERROR),

    CANCEL_ORDER_FAILED("T013", "Failed to cancel order", HttpStatus.INTERNAL_SERVER_ERROR),

    ORDER_ACCESS_DENIED("T014", "Order access denied", HttpStatus.FORBIDDEN),
    INVALID_ORDER_STATE("T015", "Invalid order state", HttpStatus.BAD_REQUEST),
    TRADE_NOT_FOUND("T016", "Trade not found", HttpStatus.NOT_FOUND),
    TRADE_ACCESS_DENIED("T017", "Trade access denied", HttpStatus.FORBIDDEN),
    WALLET_NOT_FOUND("T018", "Wallet not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_AVAILABLE_BALANCE("T019", "Insufficient available balance", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_PARAMETERS("T020", "Invalid order parameters", HttpStatus.BAD_REQUEST),

    //PORTFOLIO-WALLET

    FUND_NOT_FOUND("P001", "Fund not found", HttpStatus.NOT_FOUND),

    INVALID_AMOUNT("P002", "Invalid amount", HttpStatus.BAD_REQUEST),

    INVALID_REQUEST_PARAMETER("P003", "Invalid request parameter", HttpStatus.BAD_REQUEST),


    // SYSTEM
    INTERNAL_SERVER_ERROR("SYS001", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);


    private String code;
    private String message;
    private HttpStatus status;
}

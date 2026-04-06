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
    ;
    private String code;
    private String message;
    private HttpStatus status;
}

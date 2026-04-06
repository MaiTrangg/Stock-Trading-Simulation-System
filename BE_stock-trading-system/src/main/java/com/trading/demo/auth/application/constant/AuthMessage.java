package com.trading.demo.auth.application.constant;

public class AuthMessage {
    public static final String VERIFY_OTP_SUCCESS = "Verify OTP success";
    public static final String RESEND_OTP_SUCCESS = "Resend OTP success";
    public static final String REGISTER_SUCCESS = "Register successful";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String REFRESH_TOKEN_SUCCESS = "Refresh token successful";
    public static final String OTP_SUBJECT = "OTP Verification";
    public static final String OTP_CONTENT = "Your OTP code is: %s\nThis code will expire in %d minutes.";
    public static final int OTP_EXPIRE_MINUTES = 5;
}

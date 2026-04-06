package com.trading.demo.auth.application.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trading.demo.auth.application.constant.AuthMessage;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(AuthMessage.OTP_SUBJECT);
            helper.setText(buildOtpContent(otp), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    private String buildOtpContent(String otp) {
        return String.format(AuthMessage.OTP_CONTENT, otp, AuthMessage.OTP_EXPIRE_MINUTES);
    }
}

package com.trading.demo.auth.application.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements  EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String toEmail, String otp) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("OTP Verification");
            helper.setText(buildOtpContent(otp), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
    private String buildOtpContent(String otp) {
        return "Your OTP code is: " + otp + "\n"
                + "This code will expire in 5 minutes.";
    }
    }




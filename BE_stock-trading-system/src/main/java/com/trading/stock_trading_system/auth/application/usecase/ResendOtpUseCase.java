package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.service.EmailService;
import com.trading.stock_trading_system.auth.application.service.OtpService;
import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.domain.repository.EmailVerificationRepository;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResendOtpUseCase {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String email) {
        //1. find user by email
        User user = userRepository.findByEmail(email).orElseThrow();

        //2. soft delete old email verification in DB
        emailVerificationRepository.findByUserIdAndIsDeleteFalse(user.getId())
                .ifPresent(ev -> {
                    emailVerificationRepository.softDeleteByUserId(user.getId());
                });

        //3. generate OTP (6 numbers)
        String otp = otpService.generateOtp();

        //4. Create and save new email verification
        EmailVerification ev = new EmailVerification(
                UUID.randomUUID(),
                user.getId(),
                //encrypt OTP
                passwordEncoder.encode(otp),
                LocalDateTime.now().plusMinutes(5),
                false,
                0,
                false
        );
        emailVerificationRepository.save(ev);

        //5. send email with new OTP
        emailService.sendOtp(user.getEmail(), otp);
        System.out.println("sent email");
    }
}
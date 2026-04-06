package com.trading.demo.auth.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.demo.auth.application.service.EmailService;
import com.trading.demo.auth.application.service.OtpGenerator;
import com.trading.demo.auth.domain.enums.OtpType;
import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.domain.repository.EmailVerificationRepository;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResendOtpUseCase {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final OtpGenerator otpService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(String email) {
        // 1. find user by email
        User user = userRepository.findByEmail(email).orElseThrow();

        //2. get email verification is ACTIVE in DB
        EmailVerification oldEv = emailVerificationRepository.findActiveOtp(user.getId(), OtpType.REGISTER).orElseThrow(
                () -> new AppException(ErrorCode.EmailVerification_NOT_FOUND)
        );

        // 3. cooldown
        if (oldEv != null && oldEv.isCooldown()) {
            throw new AppException(ErrorCode.OTP_COOLDOWN);
        }

        // 4. expire old OTP
        if (oldEv != null) {
            emailVerificationRepository.markExpired(oldEv.getId());
        }

        // 5. generate OTP (6 numbers)
        String otp = otpService.generateOtp();

        //6. Create and save new email verification
        EmailVerification ev = EmailVerification.create(
                user.getId(),
                passwordEncoder.encode(otp),
                OtpType.REGISTER
        );

        try {
            emailVerificationRepository.save(ev);
        } catch (Exception e) {
            throw new AppException(ErrorCode.OTP_ALREADY_EXISTS);
        }

        // 5. send email with new OTP
        emailService.sendOtp(user.getEmail(), otp);
        System.out.println("sent email");
    }
}

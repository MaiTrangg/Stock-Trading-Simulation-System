package com.trading.demo.auth.application.usecase;

import com.trading.demo.auth.application.service.EmailService;
import com.trading.demo.auth.application.service.OtpService;
import com.trading.demo.auth.domain.enums.OtpType;
import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.domain.repository.EmailVerificationRepository;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.exception.ErrorCode;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.trading.demo.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ForgotPasswordUseCase {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Transactional
    public void execute(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));

        EmailVerification oldEv = emailVerificationRepository.findActiveOtp(user.getId(), OtpType.FORGOT_PASSWORD)
                .orElse(null);

        if (oldEv != null && oldEv.isCooldown()) {
            throw new AppException(ErrorCode.OTP_COOLDOWN);
        }

        if (oldEv != null&& oldEv.isExpired()) {
            emailVerificationRepository.markExpired(oldEv.getId());
        }

        String otp = otpService.generateOtp();

        EmailVerification ev = EmailVerification.create(
                user.getId(),
                passwordEncoder.encode(otp),
                OtpType.FORGOT_PASSWORD
        );

        emailVerificationRepository.save(ev);

        emailService.sendOtp(user.getEmail(), otp);
    }
}

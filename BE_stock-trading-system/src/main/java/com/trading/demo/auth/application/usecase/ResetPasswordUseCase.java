package com.trading.demo.auth.application.usecase;

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

import static com.trading.demo.auth.domain.enums.OtpType.FORGOT_PASSWORD;
import static com.trading.demo.common.exception.ErrorCode.*;
@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void execute(String email, String otp, String newPassword) {

        //1. find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));

        //2. find email verification
        EmailVerification ev = emailVerificationRepository
                .findActiveOtp(user.getId(), FORGOT_PASSWORD)
                .orElseThrow(() -> new AppException(ErrorCode.EmailVerification_NOT_FOUND));

        //3. check expire
        if (ev.isExpired()) {
            throw new AppException(OTP_EXPIRED);
        }
        //4. check spam otp
        if (!ev.canVerify(5)) {
            throw new AppException(OTP_TOO_MANY_ATTEMPTS);
        }
        //5. check otp
        if (!passwordEncoder.matches(otp, ev.getToken())) {
            emailVerificationRepository.increaseAttempt(ev.getId());
            throw new AppException(OTP_INVALID);
        }

        //6. change password
        user.changePassword(passwordEncoder.encode(newPassword));

        emailVerificationRepository.markVerified(ev.getId());

        userRepository.save(user);
        emailVerificationRepository.save(ev);
    }
}

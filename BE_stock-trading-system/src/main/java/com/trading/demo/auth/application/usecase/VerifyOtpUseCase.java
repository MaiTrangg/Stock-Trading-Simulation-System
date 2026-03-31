package com.trading.demo.auth.application.usecase;

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

@Service
@RequiredArgsConstructor
public class VerifyOtpUseCase {

    private final EmailVerificationRepository repo;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int MAX_ATTEMPT = 5;

    @Transactional
    public void execute(String email, String otpInput) {
        System.out.println("enter verify otp");
        //1. find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        //2. find email verification by userId with status = ACTIVE, otp, userId
        EmailVerification ev = repo
                .findActiveOtp(user.getId(), OtpType.REGISTER)
                .orElseThrow(()-> new AppException(ErrorCode.EmailVerification_NOT_FOUND));

        //5. check otp spam
        if (!ev.canVerify(MAX_ATTEMPT)) {
            throw new AppException(ErrorCode.OTP_TOO_MANY_ATTEMPTS);
        }

        //3. check otp: if true -> set (ev) status = VERIFIED and (user) status = ACTIVE -> save DB,
        //              if false -> set attemptCount + 1 (save DB)
        if (!passwordEncoder.matches(otpInput, ev.getToken())) {
            repo.increaseAttempt(ev.getId());
            throw new AppException(ErrorCode.OTP_INVALID);
        }

        //4. check expires for email verification
        if (ev.isExpired()) {
            System.out.println("otp expires");
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        //6. success
        repo.markVerified(ev.getId());
        userRepository.activateUser(user.getId());

    }
}

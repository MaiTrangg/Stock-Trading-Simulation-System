package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.domain.repository.EmailVerificationRepository;
import com.trading.stock_trading_system.common.exception.AppException;
import com.trading.stock_trading_system.common.exception.ErrorCode;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        //2. find email verification by userId and isDelete = false
        EmailVerification ev = repo
                .findByUserIdAndIsDeleteFalse(user.getId())
                .orElseThrow(()-> new AppException(ErrorCode.EmailVerification_NOT_FOUND));

        //3. check expires for email verification
        if (ev.getExpiresAt().isBefore(LocalDateTime.now())) {
            System.out.println("otp expires");
            //repo.save(ev);
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        //4. soft delete email verification if enter wrong otp so much
        if (ev.getAttemptCount() >= MAX_ATTEMPT) {
            repo.softDeleteByUserId(user.getId());
            throw new AppException(ErrorCode.OTP_TOO_MANY_ATTEMPTS);
        }


        //5. check otp: if true -> set (ev) verified = true and detele = true and (user) status = ACTIVE -> save DB,
        //              if false -> set attemptCount + 1 (save DB)
        if (!passwordEncoder.matches(otpInput, ev.getToken())) {
            repo.increaseAttempt(user.getId());
            throw new AppException(ErrorCode.OTP_INVALID);
        }

        repo.verifyOtp(ev.getId());
        userRepository.activateUser(user.getId());

    }
}

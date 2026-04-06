package com.trading.demo.user.application.usecase;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.user.application.dto.ChangePasswordRequest;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;
    private final PasswordEncoder passwordEncoder;

    public void execute(ChangePasswordRequest request) {
        UUID userId = currentUserProvider.getCurrentUserId();

        User user =
                userRepository
                        .findByIdAndIsDeleteFalse(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // check old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        String encoded = passwordEncoder.encode(request.getNewPassword());
        // check new != old
        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.PASSWORD_SAME);
        }

        user.changePassword(encoded);

        userRepository.save(user);
    }
}

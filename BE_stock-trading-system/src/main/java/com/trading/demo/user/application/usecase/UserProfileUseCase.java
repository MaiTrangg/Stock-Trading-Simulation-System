package com.trading.demo.user.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.user.application.dto.UpdateUserRequest;
import com.trading.demo.user.application.dto.UserResponse;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;
import com.trading.demo.user.infrastructure.persistence.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileUseCase {
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;
    private final UserMapper userMapper;

    public UserResponse getUserProfile() {
        UUID userId = currentUserProvider.getCurrentUserId();

        User user =
                userRepository
                        .findByIdAndIsDeleteFalse(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.create(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                user.getIsDelete(),
                user.getCreatedAt());
    }

    public UserResponse updateUserProfile(UpdateUserRequest request) {
        UUID userId = currentUserProvider.getCurrentUserId();

        User user =
                userRepository
                        .findByIdAndIsDeleteFalse(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // validate username
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        user.updateProfile(request.getUserName());

        userRepository.save(user);

        return UserResponse.create(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                user.getIsDelete(),
                user.getCreatedAt());
    }
}

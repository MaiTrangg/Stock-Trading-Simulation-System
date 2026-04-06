package com.trading.demo.user.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.user.application.constants.UserMessage;
import com.trading.demo.user.application.dto.ChangePasswordRequest;
import com.trading.demo.user.application.dto.UpdateUserRequest;
import com.trading.demo.user.application.dto.UserResponse;
import com.trading.demo.user.application.usecase.ChangePasswordUseCase;
import com.trading.demo.user.application.usecase.UserProfileUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserProfileUseCase userProfileUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @GetMapping("/get-profile")
    public ApiResponse<UserResponse> getMyProfile() {
        return new ApiResponse<UserResponse>()
                .success(UserMessage.GET_PROFILE_SUCCESS, userProfileUseCase.getUserProfile());
    }

    @PatchMapping("/update-profile")
    public ApiResponse<UserResponse> updateProfile(@RequestBody UpdateUserRequest request) {
        return new ApiResponse<UserResponse>()
                .success(UserMessage.UPDATE_PROFILE_SUCCESS, userProfileUseCase.updateUserProfile(request));
    }

    @PatchMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        changePasswordUseCase.execute(request);
        return new ApiResponse<String>().success(UserMessage.CHANGE_PASSWORD_SUCCESS, null);
    }
}

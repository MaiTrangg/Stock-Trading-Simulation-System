package com.trading.demo.auth.infrastructure.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;

@Component
public class SecurityCurrentUserProvider implements CurrentUserProvider {

    @Override
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserPrincipal) {
            CustomUserPrincipal customUser = (CustomUserPrincipal) principal;
            return customUser.getUserId();
        }

        throw new AppException(ErrorCode.UNAUTHORIZED);
    }
}

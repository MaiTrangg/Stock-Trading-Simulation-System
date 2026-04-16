package com.trading.demo.auth.infrastructure.oauth2;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.trading.demo.auth.domain.model.RefreshToken;
import com.trading.demo.auth.domain.repository.RefreshTokenRepository;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.auth.infrastructure.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        log.info("SUCCESS HANDLER CALLED");

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        UUID userId = oauthUser.getUserId();

        List<String> roles = roleRepository.findRoleByUserId(userId);

        String accessToken = jwtProvider.generateToken(userId, roles);

        String refreshTokenValue = jwtProvider.generateRefreshToken();
        refreshTokenRepository.save(RefreshToken.create(userId, refreshTokenValue));

        //redirect FE
        response.sendRedirect(
                "http://localhost:3000/oauth2/success?accessToken="
                        + accessToken
                        + "&refreshToken="
                        + refreshTokenValue
        );
    }
}

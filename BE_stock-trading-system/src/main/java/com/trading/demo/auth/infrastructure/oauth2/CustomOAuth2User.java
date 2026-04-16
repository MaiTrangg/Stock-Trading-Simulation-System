package com.trading.demo.auth.infrastructure.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.trading.demo.auth.infrastructure.security.CustomUserPrincipal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UUID userId;
    private final List<String> roles;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return userId.toString();
    }

    public CustomUserPrincipal toPrincipal() {
        return new CustomUserPrincipal(userId, roles);
    }
}
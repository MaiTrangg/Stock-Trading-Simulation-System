package com.trading.demo.auth.infrastructure.oauth2;

import java.util.List;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.trading.demo.auth.domain.enums.RoleName;
import com.trading.demo.auth.domain.model.Role;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.common.enums.ErrorCode;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {

        OAuth2User oAuth2User = super.loadUser(request);
        System.out.println("=== GOOGLE ATTRIBUTES ===");
        System.out.println(oAuth2User.getAttributes());

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String providerId = oAuth2User.getAttribute("sub");

        User user = userRepository.findByEmail(email)
                .map(existing -> {

                    //conflict: account LOCAL
                    if (existing.getPasswordHash() != null) {
                        throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTSCODE);
                    }

                    return existing;
                })
                .orElseGet(() -> createGoogleUser(name, email, providerId));

        if (!user.isActive()) {
            throw new AppException(ErrorCode.USER_INACTIVE);
        }

        List<String> roles = roleRepository.findRoleByUserId(user.getId());

        return new CustomOAuth2User(user.getId(), roles, oAuth2User.getAttributes());
    }

    private User createGoogleUser(String name, String email, String providerId) {

        User user = User.createGoogle(name, email, providerId);
        userRepository.save(user);

        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.assignRoleToUser(user.getId(), role.getId());

        return user;
    }
}

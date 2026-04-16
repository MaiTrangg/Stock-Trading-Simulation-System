package com.trading.demo.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trading.demo.auth.infrastructure.oauth2.CustomOAuth2UserService;
import com.trading.demo.auth.infrastructure.oauth2.OAuth2LoginSuccessHandler;
import com.trading.demo.auth.infrastructure.security.JwtAuthFilter;
import com.trading.demo.auth.infrastructure.security.constants.SecurityConstants;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // disable csrf (API)
                .csrf(csrf -> csrf.disable())

                // stateless
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(
                                                SecurityConstants.AUTH_LOGIN,
                                                SecurityConstants.AUTH_REGISTER,
                                                SecurityConstants.AUTH_VERIFY_OTP,
                                                SecurityConstants.AUTH_RESEND_OTP,
                                                SecurityConstants.STOCK_GET_LIST,
                                                SecurityConstants.STOCK_BY_ID,
                                                SecurityConstants.STOCK_PRICE_HISTORY,
                                                "/",
                                                "/test.html",
                                                "/static/**",
                                                "/ws/**",
                                                "/oauth2/**",
                                                "/login/oauth2/**"
                                        )

                                        .permitAll()
                                        .requestMatchers(
                                                "/api/auth/logout",
                                                "/api/auth/refresh-token",
                                                "/api/user/get-profile",
                                                "/api/user/update-profile",
                                                "/api/user/change-password")
                                        .authenticated()
                                        .anyRequest()
                                        .authenticated())

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(user -> user
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("http://localhost:3000/login?error");
                        })
                )
                // add JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.trading.demo.auth.infrastructure.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trading.demo.auth.infrastructure.security.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith(SecurityConstants.AUTH_LOGIN)
                || path.startsWith(SecurityConstants.AUTH_REGISTER)
                || path.startsWith(SecurityConstants.AUTH_VERIFY_OTP)
                || path.startsWith(SecurityConstants.AUTH_RESEND_OTP);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(SecurityConstants.HEADER_AUTH);
        //        log.info("Authorization: " + header);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        //            if (header != null && header.startsWith("Bearer ")) {
        try {
            String token = header.substring(SecurityConstants.TOKEN_PREFIX.length());
            //            log.info("TOKEN: " + token);

            if (jwtProvider.validate(token)) {

                Claims claims = jwtProvider.getClaims(token);

                UUID userId = UUID.fromString(claims.getSubject());

                List<String> roles = claims.get(SecurityConstants.CLAIM_ROLES, List.class);

                List<SimpleGrantedAuthority> authorities =
                        roles.stream()
                                .map(role -> new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + role))
                                .toList();

                CustomUserPrincipal principal = new CustomUserPrincipal(userId, roles);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(principal, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.warn("{}: {}", SecurityConstants.JWT_EXPIRED, e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            log.warn(SecurityConstants.JWT_INVALID_SIGNATURE);
        } catch (Exception e) {
            log.error(SecurityConstants.JWT_AUTH_ERROR, e);
        }

        filterChain.doFilter(request, response);
    }
}

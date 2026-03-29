package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.dto.RegisterRequest;
import com.trading.stock_trading_system.auth.application.dto.RegisterResponse;
import com.trading.stock_trading_system.auth.application.service.EmailService;
import com.trading.stock_trading_system.auth.application.service.OtpService;
import com.trading.stock_trading_system.auth.domain.enums.RoleName;
import com.trading.stock_trading_system.auth.domain.model.EmailVerification;
import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.domain.repository.EmailVerificationRepository;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.common.exception.AppException;
import com.trading.stock_trading_system.common.exception.ErrorCode;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
import com.trading.stock_trading_system.user.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    private final EmailVerificationRepository emailVerificationRepository;
    private final OtpService otpService;
    private final EmailService emailService;

    public RegisterResponse execute(RegisterRequest request) {

        //1. check email existence
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTSCODE);
        }

        //2. encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //3. create and save user  (use factory method)
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
        );
        userRepository.save(user);

        //4. assign role
        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roleRepository.assignRoleToUser(user.getId(), role.getId());

        //5. generate OTP
        String otp = otpService.generateOtp();

        //6. create and save email verification
        EmailVerification ev = new EmailVerification(
                UUID.randomUUID(),
                user.getId(),
                passwordEncoder.encode(otp),
                LocalDateTime.now().plusMinutes(3),
                false,
                0,
                false
        );
        emailVerificationRepository.save(ev);

        //7. send OTP for email
        emailService.sendOtp(user.getEmail(), otp);
        return userMapper.toRegisterResponse(user);
    }
}

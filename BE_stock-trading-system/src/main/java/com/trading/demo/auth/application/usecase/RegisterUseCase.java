package com.trading.demo.auth.application.usecase;

import com.trading.demo.auth.application.dto.RegisterRequest;
import com.trading.demo.auth.application.dto.RegisterResponse;
import com.trading.demo.auth.application.service.EmailService;
import com.trading.demo.auth.application.service.OtpService;
import com.trading.demo.auth.domain.enums.OtpType;
import com.trading.demo.auth.domain.enums.RoleName;
import com.trading.demo.auth.domain.model.EmailVerification;
import com.trading.demo.auth.domain.model.Role;
import com.trading.demo.auth.domain.repository.EmailVerificationRepository;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.common.exception.AppException;
import com.trading.demo.common.exception.ErrorCode;
import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;
import com.trading.demo.user.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        EmailVerification ev = EmailVerification.create(user.getId(),passwordEncoder.encode(otp), OtpType.REGISTER);
        emailVerificationRepository.save(ev);

        //7. send OTP for email
        emailService.sendOtp(user.getEmail(), otp);
        return userMapper.toRegisterResponse(user);
    }
}

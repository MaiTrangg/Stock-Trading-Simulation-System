package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.dto.RegisterRequest;
import com.trading.stock_trading_system.auth.application.dto.RegisterResponse;
import com.trading.stock_trading_system.auth.domain.enums.RoleName;
import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.common.exception.AppException;
import com.trading.stock_trading_system.common.exception.ErrorCode;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
import com.trading.stock_trading_system.user.infrastructure.persistence.mapper.UserMapper;
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

    public RegisterResponse execute(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTSCODE);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //use factory method
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
        );

        userRepository.save(user);
        Role role = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roleRepository.assignRoleToUser(user.getId(), role.getId());
        return userMapper.toRegisterResponse(user);
    }
}

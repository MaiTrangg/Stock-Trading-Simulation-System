package com.trading.stock_trading_system.auth.application.usecase;

import com.trading.stock_trading_system.auth.application.dto.RegisterRequest;
import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.user.domain.model.User;
import com.trading.stock_trading_system.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void execute(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email exists");
        }

//        User user = new User();
//        user.setId(UUID.randomUUID());
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
//        user.setStatus("ACTIVE");
//        user.setIsDelete(false);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        //use factory method
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
        );

        userRepository.save(user);
        Role role = roleRepository.findByName("USER")
                .orElseThrow(()-> new RuntimeException("Role not found"));
        roleRepository.assignRoleToUser(user.getId(), role.getId());
    }
}

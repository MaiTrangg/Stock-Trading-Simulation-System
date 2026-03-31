package com.trading.demo.auth.domain.repository;

import com.trading.demo.auth.domain.enums.RoleName;
import com.trading.demo.auth.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public interface RoleRepository {

    Optional<Role> findByName(RoleName name);

    void assignRoleToUser(UUID userId, UUID roleId);
    List<String> findRoleByUserId(UUID userId);
}

package com.trading.demo.auth.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trading.demo.auth.domain.enums.RoleName;
import com.trading.demo.auth.domain.model.Role;

@Service
public interface RoleRepository {

    Optional<Role> findByName(RoleName name);

    void assignRoleToUser(UUID userId, UUID roleId);

    List<String> findRoleByUserId(UUID userId);
}

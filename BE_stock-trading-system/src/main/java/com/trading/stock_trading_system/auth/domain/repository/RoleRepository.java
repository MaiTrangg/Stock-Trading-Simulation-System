package com.trading.stock_trading_system.auth.domain.repository;

import com.trading.stock_trading_system.auth.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public interface RoleRepository {

    Optional<Role> findByName(String name);

    void assignRoleToUser(UUID userId, UUID roleId);
    List<String> findRoleByUserId(UUID userId);
}

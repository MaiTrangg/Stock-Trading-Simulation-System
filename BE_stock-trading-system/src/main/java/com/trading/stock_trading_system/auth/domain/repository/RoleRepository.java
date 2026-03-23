package com.trading.stock_trading_system.auth.domain.repository;

import com.trading.stock_trading_system.auth.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface RoleRepository {

    Role findByName(String name);

    void assignRoleToUser(UUID userId, UUID roleId);
}

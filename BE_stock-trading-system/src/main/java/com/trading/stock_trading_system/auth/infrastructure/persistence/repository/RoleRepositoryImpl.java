package com.trading.stock_trading_system.auth.infrastructure.persistence.repository;

import com.trading.stock_trading_system.auth.domain.model.Role;
import com.trading.stock_trading_system.auth.domain.model.UserRole;
import com.trading.stock_trading_system.auth.domain.repository.RoleRepository;
import com.trading.stock_trading_system.auth.infrastructure.persistence.mapper.RoleMapper;
import com.trading.stock_trading_system.auth.infrastructure.persistence.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;
    private final JpaUserRoleRepository jpaUserRoleRepository;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findRoleByName(name).map(roleMapper::toDomain);
    }

    @Override
    public void assignRoleToUser(UUID userId, UUID roleId) {
        UserRole userRole = new UserRole();

        userRole.setId(UUID.randomUUID());
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        jpaUserRoleRepository.save(userRoleMapper.toEntity(userRole));
    }

    @Override
    public List<String> findRoleByUserId(UUID userId) {
        return jpaRoleRepository.findRoleByUserId(userId);
    }
}

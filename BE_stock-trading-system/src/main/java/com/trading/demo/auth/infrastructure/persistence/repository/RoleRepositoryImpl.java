package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.trading.demo.auth.domain.enums.RoleName;
import com.trading.demo.auth.domain.model.Role;
import com.trading.demo.auth.domain.model.UserRole;
import com.trading.demo.auth.domain.repository.RoleRepository;
import com.trading.demo.auth.infrastructure.persistence.mapper.RoleMapper;
import com.trading.demo.auth.infrastructure.persistence.mapper.UserRoleMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRoleRepository;
    private final JpaUserRoleRepository jpaUserRoleRepository;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return jpaRoleRepository.findRoleByName(name.name()).map(roleMapper::toDomain);
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

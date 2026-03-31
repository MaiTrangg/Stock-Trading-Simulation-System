package com.trading.demo.auth.infrastructure.persistence.repository;

import com.trading.demo.auth.infrastructure.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
}

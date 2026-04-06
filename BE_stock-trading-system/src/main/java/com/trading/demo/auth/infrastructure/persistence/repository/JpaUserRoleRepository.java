package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.demo.auth.infrastructure.persistence.entity.UserRoleEntity;

public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
}

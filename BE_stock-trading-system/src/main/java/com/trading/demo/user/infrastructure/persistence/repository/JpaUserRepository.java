package com.trading.demo.user.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.demo.user.infrastructure.persistence.entity.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = 'ACTIVE'WHERE u.id = :id ")
    void activateUser(UUID id);

    Optional<UserEntity> findByIdAndIsDeleteFalse(UUID id);
}

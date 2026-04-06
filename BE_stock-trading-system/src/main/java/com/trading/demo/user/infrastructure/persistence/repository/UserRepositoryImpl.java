package com.trading.demo.user.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.trading.demo.user.domain.model.User;
import com.trading.demo.user.domain.repository.UserRepository;
import com.trading.demo.user.infrastructure.persistence.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        jpaRepository.save(mapper.toEntity(user));
    }

    @Override
    public boolean existsByUserName(String userName) {
        return false;
    }

    @Override
    public void activateUser(UUID id) {
        jpaRepository.activateUser(id);
    }

    @Override
    public Optional<User> findByIdAndIsDeleteFalse(UUID id) {
        return jpaRepository.findByIdAndIsDeleteFalse(id).map(mapper::toDomain);
    }
}

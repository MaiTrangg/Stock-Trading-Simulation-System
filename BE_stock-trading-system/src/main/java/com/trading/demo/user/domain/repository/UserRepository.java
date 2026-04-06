package com.trading.demo.user.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.trading.demo.user.domain.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void save(User user);

    boolean existsByUserName(String userName);

    void activateUser(UUID id);

    Optional<User> findByIdAndIsDeleteFalse(UUID id);
}

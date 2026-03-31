package com.trading.demo.user.domain.repository;

import com.trading.demo.user.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void save(User user);

    public void activateUser(UUID id);
}

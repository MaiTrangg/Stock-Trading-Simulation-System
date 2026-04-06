package com.trading.demo.auth.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trading.demo.auth.infrastructure.persistence.entity.RoleEntity;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findRoleByName(String name);

    @Query(
            """
                    SELECT r.name
                    FROM RoleEntity r
                    JOIN UserRoleEntity ur ON r.id = ur.roleId
                    WHERE ur.userId = :userId
                    """)
    List<String> findRoleByUserId(UUID userId);
}

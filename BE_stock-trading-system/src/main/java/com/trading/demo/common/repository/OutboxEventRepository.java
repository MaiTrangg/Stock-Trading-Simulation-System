package com.trading.demo.common.repository;

import com.trading.demo.common.entity.OutboxEvent;
import com.trading.demo.common.enums.OutboxStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    @Query("SELECT o FROM OutboxEvent o WHERE o.status = :status ORDER BY o.createdAt ASC")
    List<OutboxEvent> findByStatusOrderByCreatedAt(@Param("status") String status, PageRequest pageRequest);

    @Query("SELECT o FROM OutboxEvent o WHERE o.status = :status ORDER BY o.createdAt ASC")
    List<OutboxEvent> findByStatusOrderByCreatedAt(@Param("status") OutboxStatus status);

    boolean existsByEventId(UUID eventId);
}

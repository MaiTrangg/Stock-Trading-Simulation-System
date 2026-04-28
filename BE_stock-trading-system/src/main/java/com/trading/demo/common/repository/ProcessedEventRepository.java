package com.trading.demo.common.repository;

import com.trading.demo.common.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, String> {
    boolean existsByEventId(String eventId);
    
    default void saveByEventId(String eventId) {
        ProcessedEventEntity entity = ProcessedEventEntity.builder()
                .eventId(eventId)
                .build();
        save(entity);
    }
}

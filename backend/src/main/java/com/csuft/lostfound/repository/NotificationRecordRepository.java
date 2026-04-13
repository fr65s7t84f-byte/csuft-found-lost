package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.NotificationRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRecordRepository extends JpaRepository<NotificationRecordEntity, Long> {
    List<NotificationRecordEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}

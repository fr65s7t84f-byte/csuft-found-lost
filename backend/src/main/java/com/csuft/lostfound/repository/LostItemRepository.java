package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.LostItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItemEntity, Long> {
    List<LostItemEntity> findByPublisherIdOrderByPublishedAtDesc(Long publisherId);
}

package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.FoundItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoundItemRepository extends JpaRepository<FoundItemEntity, Long> {
    List<FoundItemEntity> findByPublisherIdOrderByPublishedAtDesc(Long publisherId);
}

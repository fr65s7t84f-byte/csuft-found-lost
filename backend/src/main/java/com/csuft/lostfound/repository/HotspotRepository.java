package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.HotspotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotspotRepository extends JpaRepository<HotspotEntity, Long> {
}

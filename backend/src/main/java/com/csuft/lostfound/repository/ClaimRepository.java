package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
}

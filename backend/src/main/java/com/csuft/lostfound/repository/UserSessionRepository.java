package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {
    Optional<UserSessionEntity> findByTokenAndStatus(String token, String status);
}

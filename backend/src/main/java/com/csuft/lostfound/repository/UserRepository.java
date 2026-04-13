package com.csuft.lostfound.repository;

import com.csuft.lostfound.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findByNickname(String nickname);

    Optional<UserEntity> findByStudentId(String studentId);
}

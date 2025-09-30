package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProgressRepo extends JpaRepository<UserProgress,Long> {
    List<UserProgress> findByUserId(Long userId);
}

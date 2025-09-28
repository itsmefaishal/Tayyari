package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgressRepo extends JpaRepository<UserProgress,Long> {
}

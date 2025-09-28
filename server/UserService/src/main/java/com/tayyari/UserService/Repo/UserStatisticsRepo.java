package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatisticsRepo extends JpaRepository<UserStatistics,Long> {
}

package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRankingRepo extends JpaRepository<UserRanking,Long> {
}

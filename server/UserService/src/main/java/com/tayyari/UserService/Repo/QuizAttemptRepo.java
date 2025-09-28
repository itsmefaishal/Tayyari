package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepo extends JpaRepository<QuizAttempt,Long> {
}


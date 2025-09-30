package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuizAttemptRepo extends JpaRepository<QuizAttempt,Long> {
    @Query("SELECT MAX(a.attemptNumber) FROM QuizAttempt a WHERE a.userId = :userId AND a.quizId = :quizId")
    Optional<Integer> findMaxAttemptNumber(Long userId, Long quizId);

}


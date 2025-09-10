package com.QuizService.QuizService.Repository;

import com.QuizService.QuizService.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCategory(String category);
    List<Quiz> findBySubject(String subject);
    List<Quiz> findByPrev(Boolean isPrev, String cat);
}

package com.QuizService.QuizService.Repository;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCategoryIgnoreCase(String category);
    List<Quiz> findBySubjectIgnoreCase(String subject);
    List<QuizDTO> findByPrevAndCategory(Boolean prev, String category);
}

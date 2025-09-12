package com.QuizService.QuizService.Repository;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
    List<QuizDTO> findByCategory(String category);
    List<QuizDTO> findBySubject(String subject);
    List<QuizDTO> findByPrev(Boolean isPrev, String cat);
}

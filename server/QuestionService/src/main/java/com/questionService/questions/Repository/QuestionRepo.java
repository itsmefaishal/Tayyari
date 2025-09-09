package com.questionService.questions.Repository;

import com.questionService.questions.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends JpaRepository<Question, Long>, CrudRepository<Question, Long> {
}

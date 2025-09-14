package com.QuizService.QuizService.Util;

import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionDTO;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizMapperImplementation {
    QuizDTO toDto(Quiz quiz);
    Quiz toEntity(QuizDTO qDto);
    QuestionDTO toQuestionDto(Question question);
    Question toQuestion(QuestionDTO questionDTO);
}

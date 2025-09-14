package com.QuizService.QuizService.Util;

import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionDTO;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class QuizMapper implements QuizMapperImplementation{

    @Override
    public QuizDTO toDto(Quiz quiz){
        return new QuizDTO();
    }

    @Override
    public Quiz toEntity(QuizDTO quizDTO){
        return new Quiz();
    }

    @Override
    public QuestionDTO toQuestionDto(Question question){
        return new QuestionDTO();
    }

    @Override
    public Question toQuestion(QuestionDTO questionDTO){
        return new Question();
    }
}

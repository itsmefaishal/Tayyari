package com.QuizService.QuizService.Util;

import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizDTO toDto(Quiz quiz);
    Quiz toEntity(QuizDTO qDto);
}

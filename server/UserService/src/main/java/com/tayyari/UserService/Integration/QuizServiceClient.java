package com.tayyari.UserService.Integration;

import com.tayyari.UserService.DTO.QuizBasicInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "quiz-service", path = "/api/quizzes")
public interface QuizServiceClient {
    @GetMapping("/{quizId}/active")
    Boolean isQuizActive(@PathVariable("quizId") Long quizId);

    @GetMapping("/getBasicQuizInfo")
    QuizBasicInfoDto getBasicQuizInfo(Long quizId);
}

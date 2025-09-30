package com.tayyari.UserService.Integration;

import com.tayyari.UserService.DTO.QuizBasicInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "quiz-service", path = "/quiz")
public interface QuizServiceClient {

    @GetMapping("/getBasicQuizInfo")
    QuizBasicInfoDto getBasicQuizInfo(Long quizId);
}

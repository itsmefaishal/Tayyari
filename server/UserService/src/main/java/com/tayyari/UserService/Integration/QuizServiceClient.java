package com.tayyari.UserService.Integration;

import com.tayyari.UserService.DTO.QuizBasicInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;


/*@FeignClient(name = "QuizService", path = "/quiz/public")
public interface QuizServiceClient {

    @GetMapping("/get-basic-info")
    QuizBasicInfoDto getBasicQuizInfo(@RequestParam Long quizId);
}*/
@FeignClient(name = "quiz-service", url = "https://tayyari-3xqy.onrender.com")
public interface QuizServiceClient {

    @GetMapping("/quiz/public/get-basic-info")
    QuizBasicInfoDto getBasicQuizInfo(@RequestParam("quizId") Long quizId);
}

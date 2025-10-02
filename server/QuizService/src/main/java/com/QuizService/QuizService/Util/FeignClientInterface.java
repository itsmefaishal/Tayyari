package com.QuizService.QuizService.Util;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;


@FeignClient(
    name = "user-service", 
    url = "http://localhost:9090/user"
    )
    
    public interface FeignClientInterface {
   
    @PostMapping("/attempt/save")
    QuizAttemptRequestDTO submitQuizAttempt(@RequestBody QuizAttemptRequestDTO quizAttemptRequestDTO);
}
package com.QuizService.QuizService.Util;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;


@FeignClient(
    name = "user-service", 
    url = "https://tayyari-qchp.onrender.com"
    )
    
    public interface FeignClientInterface {
   
    @PostMapping("/user/attempt/save")
    QuizAttemptRequestDTO submitQuizAttempt(@RequestBody QuizAttemptRequestDTO quizAttemptRequestDTO);
}
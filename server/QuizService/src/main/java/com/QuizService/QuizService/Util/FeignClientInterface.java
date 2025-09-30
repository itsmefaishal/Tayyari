package com.QuizService.QuizService.Util;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;

@FeignClient(
    name = "user-service", 
    url = "http://localhost:9090/user-service"
)

public interface FeignClientInterface {
    
    @PostMapping("/submit-attempt")
    String submitQuizAttempt(@RequestBody QuizAttemptRequestDTO quizAttemptRequestDTO);

}
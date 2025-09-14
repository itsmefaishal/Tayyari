package com.QuizService.QuizService.Service;

import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionListDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class QuestionServiceClient {

    private final RestTemplate restTemplate;

    public QuestionServiceClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<Question> getQuestionList(List<Long> questionIds){

        String url = "http://localhost:8080/question/getMultipleQuestions";
        QuestionListDTO qList = new QuestionListDTO();
        qList.setIds(questionIds);

        ResponseEntity<List<Question>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(qList),
                new ParameterizedTypeReference<List<Question>>() {}
        );

        return response.getBody();
    }
}

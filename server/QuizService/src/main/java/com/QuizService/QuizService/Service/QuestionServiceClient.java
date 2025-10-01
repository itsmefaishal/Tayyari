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

        String url = "http://localhost:8085/question/getMultipleQuestions";
        System.out.println("calling this in http://localhost:8085/question/getMultipleQuestions in getQuestionList");
        QuestionListDTO qList = new QuestionListDTO();
        qList.setIds(questionIds);


        try {
            ResponseEntity<List<Question>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(qList.getIds()),
                new ParameterizedTypeReference<List<Question>>() {}
            );


            System.out.println(response.getBody() + "print in getQuestionList data");
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("inside try of restCLinet");

            throw new RuntimeException();
        }

    }
}

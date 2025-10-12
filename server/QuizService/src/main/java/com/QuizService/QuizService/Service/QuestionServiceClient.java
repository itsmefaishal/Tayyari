package com.QuizService.QuizService.Service;
import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceClient.class);

    public QuestionServiceClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<Question> getQuestionList(List<Long> questionIds){
        logger.info( "Inside Quiz service getQuestionList()");

        String url = "https://tayyari-dbt8.onrender.com/question/getMultipleQuestions";
        // String url = "http://localhost:8085/question/getMultipleQuestions";
        System.out.println("calling this in https://tayyari-dbt8.onrender.com/question/getMultipleQuestions in getQuestionList");
        QuestionListDTO qList = new QuestionListDTO();
        qList.setIds(questionIds);

        try {
            logger.info( "Inside Quiz service QuestionServiceClient -> getQuestionList() -> try block");

            ResponseEntity<List<Question>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(qList.getIds()),
                new ParameterizedTypeReference<List<Question>>() {}
            );

            logger.info( "Inside Quiz service QuestionServiceClient -> getQuestionList() -> try block returning List<Question>: " + response.getBody().toString());
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();

            logger.info( "Inside Quiz service -> QuestionServiceClient -> getQuestionList() -> catch block");

            throw new RuntimeException();
        }
    }
}

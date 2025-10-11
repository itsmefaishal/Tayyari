package com.QuizService.QuizService.Controller;

import com.QuizService.QuizService.DTO.QuizAttemptResponseDTO;
import com.QuizService.QuizService.DTO.QuizBasicInfo;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.DTO.QuizResponseDTO;
import com.QuizService.QuizService.DTO.QuizWithQuestions;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/quiz/public")
public class QuizControllerPublic {

    @Autowired
    private QuizService quizService;

    @GetMapping("/test")
    public ResponseEntity<String> testPoint(){
        return ResponseEntity.ok("Quiz Service Working !!!");
    }

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizWithQuestions> getQuiz(@PathVariable Long quizId){
        System.out.println("inside get_quiz controller");
        return ResponseEntity.status(200).body(quizService.getQuizWithQuestions(quizId));
    }

    @GetMapping("/get-multiple-quiz")
    public ResponseEntity<List<QuizDTO>> getQuiz(@RequestParam int page){
        System.out.println("inside get_quiz all controller");
        return ResponseEntity.status(200).body(quizService.getAllQuiz(page));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Quiz>> getQuizByCat(@PathVariable String category){
        System.out.println("inside get_quiz_by_category controller");
        return ResponseEntity.ok(quizService.getQuizByCategory(category));
    }

    @GetMapping("/subject/{sub}")
    public ResponseEntity<List<Quiz>> getQuizBySub(@PathVariable String sub){
        System.out.println("inside get_quiz_by_subject controller");
        return ResponseEntity.ok(quizService.getQuizBySubject(sub));
    }

    @GetMapping("/prev/{prev-cat}")
    public ResponseEntity<List<QuizDTO>> getPrev(@PathVariable Boolean prev, @PathVariable String category){
        System.out.println("inside get_quiz_by_prev controller");
        return ResponseEntity.ok(quizService.getByPrev(prev,category));
    }

    @PostMapping("/submit-quiz")
    public ResponseEntity<QuizAttemptResponseDTO> submitQuiz(@RequestParam String uniqueKey, @RequestBody QuizResponseDTO request) throws Exception{
        System.out.println("inside submitQuiz controller");
        return ResponseEntity.ok(quizService.submitQuiz(uniqueKey,request));
    }

    @GetMapping("/get-basic-info")
    public QuizBasicInfo getBasicQuizInfo(@RequestParam Long quizId){
        System.out.println("inside get-basic-info-------------- :  ");
        QuizBasicInfo quizBasicInfo = quizService.getQuizBasicInfo(quizId);
        System.out.println(quizBasicInfo.toString());
        return quizBasicInfo;
    }
}

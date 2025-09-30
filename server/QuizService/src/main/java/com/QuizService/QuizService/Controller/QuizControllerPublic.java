package com.QuizService.QuizService.Controller;

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

// @CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/quiz/public")
public class QuizControllerPublic {

    @Autowired
    private QuizService quizService;

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizWithQuestions> getQuiz(@PathVariable Long quizId){
        return ResponseEntity.status(200).body(quizService.getQuizWithQuestions(quizId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<QuizDTO>> getQuiz(){
        return ResponseEntity.status(200).body(quizService.getAllQuiz());
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Quiz>> getQuizByCat(@PathVariable String category){
        return ResponseEntity.ok(quizService.getQuizByCategory(category));
    }

    @GetMapping("/subject/{sub}")
    public ResponseEntity<List<Quiz>> getQuizBySub(@PathVariable String sub){
        return ResponseEntity.ok(quizService.getQuizBySubject(sub));
    }

    @GetMapping("/prev/{prev-cat}")
    public ResponseEntity<List<QuizDTO>> getPrev(@PathVariable Boolean prev, @PathVariable String category){
        return ResponseEntity.ok(quizService.getByPrev(prev,category));
    }

    @GetMapping("/submit-quiz")
    public ResponseEntity<?> submitQuiz(@RequestParam String uniqueKey, @RequestBody QuizResponseDTO request){
        return ResponseEntity.ok(quizService.submitQuiz(uniqueKey,request));
    }

    @GetMapping("/get-basic-info")
    public ResponseEntity<QuizBasicInfo> getBasicQuizInfo(@RequestParam Long quizId){
        return ResponseEntity.ok(quizService.getQuizBasicInfo(quizId));
    }
}

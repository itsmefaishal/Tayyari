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

// @CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/quiz/admin")
public class QuizControllerAdmin {

    @Autowired
    private QuizService quizService;

    @GetMapping("/get/{quizId}")
    public ResponseEntity<QuizWithQuestions> getQuiz(@PathVariable Long quizId){
        System.out.println("inside quiz admin get id");
        return ResponseEntity.status(200).body(quizService.getQuizWithQuestions(quizId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<QuizDTO>> getQuiz(){
        return ResponseEntity.status(200).body(quizService.getAllQuiz());
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteMultipleQuiz(@RequestBody List<Long> ids){
        quizService.deleteMultipleQuiz(ids);

        return ResponseEntity.ok("Quizzes deleted successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.addQuiz(quiz));
    }

    @PostMapping("/add/all")
    public ResponseEntity<String> addQuiz(@RequestBody List<Quiz> quizList){
        return ResponseEntity.ok(quizService.addQuizzes(quizList));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.updateQuiz(id, quiz));
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

    @GetMapping("/get-basic-info")
    public ResponseEntity<QuizBasicInfo> getBasicQuizInfo(@RequestParam Long quizId){
        System.out.println("inside getBasicQuizInfo controller");
        return ResponseEntity.ok(quizService.getQuizBasicInfo(quizId));
    }

    @GetMapping("/submit-quiz")
    public ResponseEntity<QuizAttemptResponseDTO> submitQuiz(@RequestParam String uniqueKey, @RequestBody QuizResponseDTO request) throws Exception{
        System.out.println("inside submitQuiz controller");
        return ResponseEntity.ok(quizService.submitQuiz(uniqueKey,request));
    }
}
package com.QuizService.QuizService.Controller;

import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.DTO.QuizWithQuestions;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizWithQuestions> getQuiz(@PathVariable Long id){
        return ResponseEntity.status(200).body(quizService.getQuizWithQuestions(id));
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

    @PostMapping("/add-quizzes")
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
}

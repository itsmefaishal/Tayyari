package com.QuizService.QuizService.Controller;

import com.QuizService.QuizService.DTO.QuizDTO;
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
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long id){
        return ResponseEntity.status(200).body(quizService.getQuiz(id));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteMultipleQuiz(@RequestBody List<Long> ids){
        quizService.deleteMultipleQuiz(ids);

        return ResponseEntity.ok("Quizzes deleted successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<QuizDTO> addQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.addQuiz(quiz));
    }

    @PostMapping("/update")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.updateQuiz(id, quiz));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<QuizDTO>> getQuizByCat(@PathVariable String cat){
        return ResponseEntity.ok(quizService.getQuizByCategory(cat));
    }

    @GetMapping("/{subject}")
    public ResponseEntity<List<QuizDTO>> getQuizBySub(@PathVariable String sub){
        return ResponseEntity.ok(quizService.getQuizBySubject(sub));
    }

    @GetMapping("/{prev+cat}")
    public ResponseEntity<List<QuizDTO>> getPrev(@PathVariable Boolean prev, @PathVariable String cat){
        return ResponseEntity.ok(quizService.getByPrev(prev,cat));
    }
}

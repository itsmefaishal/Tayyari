package com.questionService.questions.Controller;

import com.questionService.questions.Entity.Question;
import com.questionService.questions.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Question>> getQuestion(@PathVariable Long id){
        try{
            Optional<Question> q = questionService.getQuestion(id);

            if(q == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(q);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/getMultipleQuestions")
    public ResponseEntity<List<Optional<Question>>> getMultipleQuestions(@RequestBody List<Long> ids){
        try{
            List<Optional<Question>> list = questionService.getMultipleQuestions(ids);

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        try{
            System.out.println(question.getQuestionContent());
            return ResponseEntity.ok(questionService.addQuestion(question));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addMultipleQuestions")
    public ResponseEntity<String> addMultipleQuestion(@RequestBody List<Question> list){
        try{
            return ResponseEntity.ok(questionService.addMultipleQuestions(list));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/updateQuestion/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question){
        try{
            return ResponseEntity.ok(questionService.updateQuestion(id, question));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){
        try{
            return ResponseEntity.ok(questionService.deleteQuestion(id));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteQuestions(@RequestBody List<Long> list){
        try{
            return ResponseEntity.ok(questionService.deleteMultipleQuestions(list));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

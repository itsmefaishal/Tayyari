package com.questionService.questions.Controller;

import com.questionService.questions.Entity.Question;
import com.questionService.questions.QuestionDTO.QuestionDTO;
import com.questionService.questions.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Question> getQuestion(@PathVariable Long id){
        try{
            Question q = questionService.getQuestion(id);

            if(q==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(q);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/getMultipleQuestions")
    public ResponseEntity<List<Question>> getMultipleQuestions(@RequestBody List<Long> ids){
        try{
            List<Question> list = questionService.getMultipleQuestions(ids);

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionDTO question){
        try{
            System.out.println(question.getQuestionContent());
            return ResponseEntity.ok(questionService.addQuestion(question));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addMultipleQuestions")
    public ResponseEntity<String> addMultipleQuestion(@RequestBody List<QuestionDTO> list){
        try{
            return ResponseEntity.ok(questionService.addMultipleQuestions(list));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

<<<<<<< HEAD
    @PatchMapping("/updateQuestion/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question){
=======
    @PostMapping("/updateQuestion/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO question){
>>>>>>> remotes/origin/main
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

    @PostMapping("/search")
    public Page<Question> searchQuestions(
            @RequestBody QuestionDTO dto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return questionService.searchQuestions(dto, pageable);
    }
}

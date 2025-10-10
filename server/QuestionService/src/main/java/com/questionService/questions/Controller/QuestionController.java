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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController 
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/test")
    public ResponseEntity<String> testPoint(){
        return ResponseEntity.ok("Question Service Working !!!");
    }

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
    public List<Question> getMultipleQuestions(@RequestBody List<Long> ids){
        try{
            System.out.println(ids + "this is from question controller getMultiplequestions method this is request ids");
            
            List<Question> list = questionService.getMultipleQuestions(ids);
            System.out.println(list + "this is from question controller getMultiplequestions method this is the list of question for response");
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionDTO question,
                                                @RequestHeader("X-User-Id") String uName){
        try{
            System.out.println(question.getQuestionContent());
            return ResponseEntity.ok(questionService.addQuestion(question,uName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addMultipleQuestions")
    public ResponseEntity<String> addMultipleQuestion(@RequestBody List<QuestionDTO> list,
                                                        @RequestHeader("X-User-Id") String userName){
        try{
            return ResponseEntity.ok(questionService.addMultipleQuestions(list,userName));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateQuestion/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                   @RequestBody Question question,
                                                   @RequestHeader("X-User-Id") String uName){
        try{
            return ResponseEntity.ok(questionService.updateQuestion(id, question,uName));
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
        Page<Question> questions = questionService.searchQuestions(dto, pageable);
       // questions.getTotalPages()
        return questions;
    }
}

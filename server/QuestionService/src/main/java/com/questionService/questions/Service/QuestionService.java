package com.questionService.questions.Service;

import com.questionService.questions.Entity.Question;
import com.questionService.questions.QuestionDTO.QuestionDTO;
import com.questionService.questions.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public Optional<Question> getQuestion(Long id){
        try{
            return questionRepo.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Optional<Question>> getMultipleQuestions(List<Long> listOfIds){
        try{
            List<Optional<Question>> questionList = new ArrayList<>();

            for(Long id : listOfIds){
                Optional<Question> q = getQuestion(id);
                questionList.add(q);
            }

            return questionList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String addQuestion(Question request){
        try{
            Question question = new Question();
            question.setCategory(request.getCategory());
            question.setQuestionContent(request.getQuestionContent());
            question.setCorrectAns(request.getCorrectAns());
            question.setDifficulty(request.getDifficulty());
            question.setImageUrl(request.getImageUrl());
            question.setMarks(request.getMarks());
            question.setOptionOne(request.getOptionOne());
            question.setOptionTwo(request.getOptionTwo());
            question.setOptionThree(request.getOptionThree());
            question.setOptionFour(request.getOptionFour());
            if(request.getMultipleChoice() != null){
                question.setMultipleChoice(request.getMultipleChoice());
            }
            question.setNegativeMarks(request.getNegativeMarks());
            question.setCreatedAt(LocalDate.now());

            questionRepo.save(question);

            return "One question added successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String addMultipleQuestions(List<Question> list){
        try{
            for(Question q : list) {
                addQuestion(q);
            }

            return "All questions added successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Question updateQuestion(Long qId, Question request){
        try{
            Optional<Question> q = questionRepo.findById(qId);
            Question question = q.get();

            if(request.getCategory() != null){
                question.setCategory(request.getCategory());
            }

            if(request.getOptionOne() != null){
                question.setOptionOne(request.getOptionOne());
            }

            if(request.getOptionTwo() != null){
                question.setOptionTwo(request.getOptionTwo());
            }

            if(request.getOptionThree() != null){
                question.setOptionThree(request.getOptionThree());
            }

            if(request.getOptionFour() != null){
                question.setOptionFour(request.getOptionFour());
            }

            if(request.getCorrectAns() != null){
                question.setCorrectAns(request.getCorrectAns());
            }

            if(request.getQuestionContent() != null){
                question.setQuestionContent(request.getQuestionContent());
            }

            if(request.getMultipleChoice() != null){
                question.setMultipleChoice(request.getMultipleChoice());
            }

            if((Integer)request.getNegativeMarks() != null){
                question.setNegativeMarks(request.getNegativeMarks());
            }

            if(request.getDifficulty() != null){
                question.setDifficulty(request.getDifficulty());
            }

            if(request.getImageUrl() != null){
                question.setImageUrl(request.getImageUrl());
            }

            if((Integer)request.getMarks() != null){
                question.setMarks(request.getMarks());
            }

            questionRepo.save(question);

            return question;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteQuestion(Long qId){
        try{
            questionRepo.deleteById(qId);

            return "Question deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteMultipleQuestions(List<Long> list){
        try{
            for(Long id : list){
                deleteQuestion(id);
            }

            return "All questions deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

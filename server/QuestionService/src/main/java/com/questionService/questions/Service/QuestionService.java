package com.questionService.questions.Service;

import com.questionService.questions.Entity.Question;
import com.questionService.questions.QuestionDTO.QuestionDTO;
import com.questionService.questions.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public Question getQuestion(Long id){
        try{
            Question q = questionRepo.findById(id)
                    .orElseThrow(()-> new RuntimeException("Question not found for this id"));

            return q;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Question> getMultipleQuestions(List<Long> listOfIds){
        try{
            List<Question> questionList = new ArrayList<>();

            for(Long id : listOfIds){
                Question q = getQuestion(id);
                if(q == null) continue;
                questionList.add(q);
            }

            return questionList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String addQuestion(QuestionDTO request,String user){
        try{
            Question question = new Question();
            question.setCreatedBy(user);
            question.setCategory(request.getCategory());
            question.setSubCat(request.getSubCat());
            question.setSubject(request.getSubject());
            question.setQuestionContent(request.getQuestionContent());
            question.setCorrectAns(request.getCorrectAns());
            question.setDifficulty(request.getDifficulty());
            question.setImageUrl(request.getImageUrl());
            question.setMarks(request.getMarks());
            question.setSubject(request.getSubject());
            question.setOptionOne(request.getOptionOne());
            question.setOptionTwo(request.getOptionTwo());
            question.setOptionThree(request.getOptionThree());
            question.setOptionFour(request.getOptionFour());
            question.setNegativeMarks(request.getNegativeMarks());
            if(request.getMultipleChoice() != null){
                question.setMultipleChoice(request.getMultipleChoice());
            }
            question.setCreatedAt(LocalDate.now());

            questionRepo.save(question);

            return "One question added successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String addMultipleQuestions(List<QuestionDTO> list,String uName){
        try{
            for(QuestionDTO q : list) {
                addQuestion(q,uName);
            }

            return "All questions added successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Question updateQuestion(Long qId, Question request,String userName){
        try{
            Optional<Question> q = questionRepo.findById(qId);
            Question question = q.get();

            if(request.getCategory() != null){
                question.setCategory(request.getCategory());
            }

            if(request.getSubject() != null){
                question.setSubject(request.getSubject());
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
            if(request.getSubCat() != null){
                question.setSubCat(request.getSubCat());
            }
            if(request.getSubject() != null){
                question.setSubject(request.getSubject());
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

            if(request.getSubCat() != null){
                question.setSubCat(request.getSubCat());
            }
            question.setUpdatedAt(LocalDate.now());
            question.setUpdatedBy(userName);


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

    public Page<Question> searchQuestions(QuestionDTO dto, Pageable pageable) {
        return questionRepo.findAll(QuestionSpecification.filterBy(dto), pageable);
    }
}

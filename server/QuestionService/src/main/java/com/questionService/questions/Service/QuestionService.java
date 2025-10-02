package com.questionService.questions.Service;
import com.questionService.questions.Entity.Question;
import com.questionService.questions.QuestionDTO.QuestionDTO;
import com.questionService.questions.Repository.QuestionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionRepo questionRepo;

    public Question getQuestion(Long id){
        try{
            logger.info("Inside QuestionService -> getQuestion() -> try block taking param Long: "+ id);
            Question q = questionRepo.findById(id)
                    .orElseThrow(()-> new RuntimeException("Question not found for this id"));

            logger.info("Inside QuestionService -> getQuestion() -> try block returning param Question: "+ q.toString());
            return q;
        } catch (Exception e) {
            logger.error("Inside QuestionService -> getQuestion() -> catch block");
            throw new RuntimeException(e);
        }
    }

    public List<Question> getMultipleQuestions(List<Long> listOfIds){
        try{
            logger.info("Inside QuestionService -> getMultipleQuestions() -> try block taking param List<Long> listOfIds: "+ listOfIds);

            List<Question> questionList = new ArrayList<>();

            for(Long id : listOfIds){
                Question q = getQuestion(id);
                if(q == null) continue;
                questionList.add(q);
            }

            logger.info("Inside QuestionService -> getMultipleQuestions() -> try block returning List<Question>: "+ questionList);

            return questionList;
        } catch (Exception e) {
            logger.error("Inside QuestionService -> getMultipleQuestions() -> catch block");

            throw new RuntimeException(e);
        }
    }

    public String addQuestion(QuestionDTO request,String user){
        try{
            logger.info("Inside QuestionService -> addQuestion() -> try block takin param QuestionDTO: " + request.toString() + " & user: "+ user);

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

            logger.info("Inside QuestionService -> addQuestion() -> try block returning");

            return "One question added successfully";
        } catch (Exception e) {            
            logger.error("Inside QuestionService -> addQuestion() -> catch block");

            throw new RuntimeException(e);
        }
    }

    public String addMultipleQuestions(List<QuestionDTO> list,String uName){
        try{
            logger.info("Inside QuestionService -> addMultipleQuestions() -> try block taking param List<QuestionDTO>: " + list + "& String: " + uName);

            for(QuestionDTO q : list) {
                addQuestion(q,uName);
            }
            logger.info("Inside QuestionService -> addMultipleQuestions() -> try block returning");

            return "All questions added successfully";
        } catch (Exception e) {
            logger.error("Inside QuestionService -> addMultipleQuestions() -> catch block");

            throw new RuntimeException(e);
        }
    }

    public Question updateQuestion(Long qId, Question request,String userName){
        try{
            logger.info("Inside QuestionService -> updateQuestion() -> try block returning taking param Long: " + qId + " & Question: " + request + " & String: " + userName);

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

            logger.info("Inside QuestionService -> updateQuestion() -> try block returning Question: " + question.toString());

            return question;
        } catch (Exception e) {
            logger.error("Inside QuestionService -> updateQuestion() -> catch block");

            throw new RuntimeException(e);
        }
    }

    public String deleteQuestion(Long qId){
        try{
            logger.info("Inside QuestionService -> deleteQuestion() -> try block taking param Long: "+ qId);

            questionRepo.deleteById(qId);

            logger.info("Inside QuestionService -> deleteQuestion() -> try block returning");

            return "Question deleted successfully";
        } catch (Exception e) {
            logger.error("Inside QuestionService -> deleteQuestion() -> catch block");

            throw new RuntimeException(e);
        }
    }

    public String deleteMultipleQuestions(List<Long> list){
        try{

            logger.info("Inside QuestionService -> deleteMultipleQuestions() -> try block taking param List<Long>: "+ list);

            for(Long id : list){
                deleteQuestion(id);
            }

            return "All questions deleted successfully";
        } catch (Exception e) {
            logger.error("Inside QuestionService -> deleteMultipleQuestions() -> catch block");
            
            throw new RuntimeException(e);
        }
    }

    public Page<Question> searchQuestions(QuestionDTO dto, Pageable pageable) {
        logger.info("Inside QuestionService -> deleteMultipleQuestions() -> try block taking param QuestionDTO: "+dto.toString() + " & Pageable: "+pageable);
        
        return questionRepo.findAll(QuestionSpecification.filterBy(dto), pageable);
    }
}

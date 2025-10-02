package com.QuizService.QuizService.Service;
import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionDTO;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;
import com.QuizService.QuizService.DTO.QuizBasicInfo;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.DTO.QuizResponseDTO;
import com.QuizService.QuizService.DTO.QuizWithQuestions;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Exception.NotFoundException;
import com.QuizService.QuizService.Repository.QuizRepo;
import com.QuizService.QuizService.Util.FeignClientInterface;
import com.QuizService.QuizService.Util.QuizAttemptCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    FeignClientInterface feignClientInterface;

    @Autowired
    private QuizAttemptCalculation quizAttemptCalculation;

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuestionServiceClient questionServiceClient;

    public QuizDTO getQuiz(Long id){
        Quiz q = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found"));

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(q.getId());
        quizDTO.setTitle(q.getTitle());
        quizDTO.setPrev(q.getPrev());
        quizDTO.setCategory(q.getCategory());
        quizDTO.setDescription(q.getDescription());
        quizDTO.setDuration(q.getDuration());
        quizDTO.setPassMarks(q.getPassMarks());
        quizDTO.setSubject(q.getSubject());
        quizDTO.setTotalMarks(q.getTotalMarks());
        quizDTO.setQuestionList(q.getQuestionList());
        quizDTO.setStatus(q.getStatus());

        return quizDTO;
    }

    public String deleteQuiz(Long id){
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + id));
        quizRepo.delete(quiz);

        return "Quiz deleted";
    }

    public List<QuizDTO> getAllQuiz(){
        try{
            List<QuizDTO> list = new ArrayList<>();
            list.add(getQuiz((long)7L));
            return list;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteMultipleQuiz(List<Long> ids){
        try{
            for(long id : ids){
                deleteQuiz(id);
            }

            return "Deleted the quizzes";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Quiz addQuiz(Quiz quiz){
        return quizRepo.save(quiz);
    }

    public String addQuizzes(List<Quiz> list){
        for(Quiz q : list){
            addQuiz(q);
        }
        return "All Quizzes added";
    }

    public Quiz updateQuiz(Long id, Quiz quiz){
        Quiz q = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz does not exist"));

        if(quiz.getPrev() != null){
            q.setPrev(quiz.getPrev());
        }

        if(quiz.getCategory() != null){
            q.setCategory(quiz.getCategory());
        }

        if(quiz.getQuestionList() != null && !quiz.getQuestionList().isEmpty()){
            q.setQuestionList(quiz.getQuestionList());
        }

        if((Integer)quiz.getDuration() != null){
            q.setDuration(quiz.getDuration());
        }

        if((Double)quiz.getTotalMarks() != null){
            q.setTotalMarks(quiz.getTotalMarks());
        }

        if((Double)quiz.getPassMarks() != null){
            q.setPassMarks(quiz.getPassMarks());
        }

        if(quiz.getTitle() != null){
            q.setTitle(quiz.getTitle());
        }

        if(quiz.getDescription() != null){
            q.setDescription(quiz.getDescription());
        }

        quizRepo.save(q);

        return q;
    }

    public QuizWithQuestions getQuizWithQuestions(Long quizId){
        QuizDTO quiz = getQuiz(quizId);

        List<Question> questions = questionServiceClient.getQuestionList(quiz.getQuestionList());
        System.out.println(questions.toString());

        List<QuestionDTO> questionList = changeToDto(questions);
        System.out.println("inside quiz servie admin get id getQuizWithQuestions");

        return new QuizWithQuestions(quiz, questionList);
    }

    public List<Quiz> getQuizByCategory(String category){
        return quizRepo.findByCategoryIgnoreCase(category);
    }

    public List<Quiz> getQuizBySubject(String subject){
        List<Quiz> list = quizRepo.findBySubjectIgnoreCase(subject);
        System.out.println(list);
        return list;
    }

    public List<QuestionDTO> changeToDto(List<Question> qList){
        List<QuestionDTO> qDtoList = new ArrayList<>();

        for(Question q : qList){
            QuestionDTO qDto = new QuestionDTO();
            qDto.setContent(q.getQuestionContent());
            qDto.setCorrectAns(q.getCorrectAns());
            qDto.setOptionA(q.getOptionOne());
            qDto.setOptionB(q.getOptionTwo());
            qDto.setOptionC(q.getOptionThree());
            qDto.setOptionD(q.getOptionFour());
            qDto.setMarks(q.getMarks());

            qDtoList.add(qDto);
        }
        System.out.println("inside quiz servie admin get id changeToDto");


        return qDtoList;
    }

    public List<QuizDTO> getByPrev(Boolean prev, String category){
        return quizRepo.findByPrevAndCategory(prev, category);
    }

    public String submitQuiz(String uniqueKey, QuizResponseDTO quizWithQuestions) throws Exception{
        Long quizIdFromRequest = quizWithQuestions.getQuizId();
        
        QuizWithQuestions qWithQuestions = getQuizWithQuestions(quizIdFromRequest);

        QuizAttemptRequestDTO quizAttemptRequestDTO = quizAttemptCalculation.calculateAttempt(qWithQuestions, quizWithQuestions, uniqueKey);
        System.out.println(quizAttemptRequestDTO.toString() + "this is from submitQuiz inside quizService");

        return feignClientInterface.submitQuizAttempt(quizAttemptRequestDTO);
    }

    public QuizBasicInfo getQuizBasicInfo(Long quizId){
        QuizDTO quiz = getQuiz(quizId);

        QuizBasicInfo quizBasicInfo = new QuizBasicInfo();
        quizBasicInfo.setQuizId(quiz.getId());
        quizBasicInfo.setQuizDescription(quiz.getDescription());
        quizBasicInfo.setStatus(quiz.getStatus());
        quizBasicInfo.setQuizName(quiz.getTitle());
        System.out.println(quizBasicInfo.toString()+"Sent form method  getQuizBasicInfo");

        return quizBasicInfo;
    }
}
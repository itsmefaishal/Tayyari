package com.QuizService.QuizService.Service;
import com.QuizService.QuizService.DTO.Question;
import com.QuizService.QuizService.DTO.QuestionDTO;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;
import com.QuizService.QuizService.DTO.QuizAttemptResponseDTO;
import com.QuizService.QuizService.DTO.QuizBasicInfo;
import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.DTO.QuizResponseDTO;
import com.QuizService.QuizService.DTO.QuizWithQuestions;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Exception.NotFoundException;
import com.QuizService.QuizService.Repository.QuizRepo;
import com.QuizService.QuizService.Util.FeignClientInterface;
import com.QuizService.QuizService.Util.QuizAttemptCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    private static final Logger logger = LoggerFactory.getLogger(Quiz.class);

    public QuizDTO getQuiz(Long id){
        logger.info("Inside Quiz service -> getQuiz() accepting param "+id);
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

        logger.info("Inside Quiz service exiting getQuiz() returning \n"+ quizDTO.toString());
        return quizDTO;
    }

    public String deleteQuiz(Long id){
        logger.info("Inside Quiz service -> deleteQuiz() accepting param "+id);
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + id));
        quizRepo.delete(quiz);

        logger.info("Inside Quiz service exiting deleteQuiz()");
        return "Quiz deleted";
    }

    public List<QuizDTO> getAllQuiz(int page){
        try{
            logger.info("Inside Quiz service exiting getAllQuiz()");

            PageRequest pageable = PageRequest.of(page, 13);
            Page<Quiz> quizPage = quizRepo.findAll(pageable);

            List<QuizDTO> list = quizPage.stream().map(this::convertToQuizDTO).toList();

            logger.info("Inside Quiz service -> getAllQuiz() returning List<QuizDTO> : " + list);
            return list;
        } catch (RuntimeException e) {
            logger.error( "Inside Quiz service exiting getAllQuiz() with error: " + e);
            throw new RuntimeException(e);
        }
    }

    public String deleteMultipleQuiz(List<Long> ids){
        try{
            logger.info("Inside Quiz service entering deleteMultipleQuiz() with param: List<Long> " + ids.toString());

            for(long id : ids){
                deleteQuiz(id);
            }

            logger.info("Inside Quiz service exiting deleteMultipleQuiz()");
            return "Deleted the quizzes";
        } catch (Exception e) {
            logger.error( "Inside Quiz service exiting deleteMultipleQuiz() with error: " + e);
            throw new RuntimeException(e);
        }
    }

    public Quiz addQuiz(Quiz quiz){
        logger.info( "Inside Quiz service entering addQuiz() with param: " + quiz.toString());
        return quizRepo.save(quiz);
    }

    public String addQuizzes(List<Quiz> list){
        logger.info( "Inside Quiz service entering addQuizzes() with param: List<Quiz>" + list.toString());
        for(Quiz q : list){
            addQuiz(q);
        }

        logger.info( "Inside Quiz service exiting addQuizzes()");
        return "All Quizzes added";
    }

    public Quiz updateQuiz(Long id, Quiz quiz){
        logger.info( "Inside Quiz service entering updateQuiz() with param: Long: " + id + " & Quiz: "+ quiz.toString());

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

        logger.info( "Inside Quiz service exiting updateQuiz() with value Quiz: "+ q.toString());
        return q;
    }

    public QuizWithQuestions getQuizWithQuestions(Long quizId){
        logger.info( "Inside Quiz service entering getQuizWithQuestions() with param Long: " + quizId);

        QuizDTO quiz = getQuiz(quizId);

        List<Question> questions = questionServiceClient.getQuestionList(quiz.getQuestionList());
        System.out.println(questions.toString());

        List<QuestionDTO> questionList = changeToDto(questions);
        QuizWithQuestions q = new QuizWithQuestions(quiz, questionList);
        
        logger.info( "Inside Quiz service exiting getQuizWithQuestions() with value QuizWithQuestions: "+ q.toString());

        return q;
    }

    public List<Quiz> getQuizByCategory(String category){
        logger.info( "Inside Quiz service entering getQuizByCategory() with param category: "+ category);
        
        return quizRepo.findByCategoryIgnoreCase(category);
    }

    public List<Quiz> getQuizBySubject(String subject){
        logger.info( "Inside Quiz service entering getQuizBySubject() with param subject: "+ subject);

        List<Quiz> list = quizRepo.findBySubjectIgnoreCase(subject);

        logger.info( "Inside Quiz service exiting getQuizBySubject() with value List<Quiz>: "+ list.toString());

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
            qDto.setSubject(q.getSubject());
            qDto.setNegativeMarks(q.getNegativeMarks());

            qDtoList.add(qDto);
        }
        logger.info("inside quiz servie admin get id changeToDto");


        return qDtoList;
    }

    public List<QuizDTO> getByPrev(Boolean prev, String category){
        logger.info( "Inside Quiz service getByPrev() with response List<QuizDTO>");

        return quizRepo.findByPrevAndCategory(prev, category);
    }

    public QuizAttemptResponseDTO submitQuiz(String uniqueKey, QuizResponseDTO quizWithQuestions) throws Exception{
        logger.info( "Inside Quiz service submitQuiz()");

        Long quizIdFromRequest = quizWithQuestions.getQuizId();
        
        QuizWithQuestions qWithQuestions = getQuizWithQuestions(quizIdFromRequest);

        QuizAttemptRequestDTO quizAttemptRequestDTO = quizAttemptCalculation.calculateAttempt(qWithQuestions, quizWithQuestions, uniqueKey);
        System.out.println(quizAttemptRequestDTO.toString() + "this is from submitQuiz inside quizService");

        QuizAttemptRequestDTO responseAfterQuizSubmit = feignClientInterface.submitQuizAttempt(quizAttemptRequestDTO);

        logger.info( "Inside Quiz service submitQuiz() printing QuizAttemptRequestDTO responseAfterQuizSubmit: " + responseAfterQuizSubmit.toString());

        // try{
            QuizAttemptResponseDTO response = new QuizAttemptResponseDTO(responseAfterQuizSubmit.getUniqueKey(), responseAfterQuizSubmit.getScore(), responseAfterQuizSubmit.getMaxScore(),responseAfterQuizSubmit.getTimeTaken(),responseAfterQuizSubmit.getCorrectAnswers(),responseAfterQuizSubmit.getIncorrectAnswers(),responseAfterQuizSubmit.getUnanswered());
        // }

        logger.info( "Inside Quiz service exiting submitQuiz() with value QuizAttemptResponseDTO: " + response.toString());

        return response;
    }

    public QuizBasicInfo getQuizBasicInfo(Long quizId){
        logger.info( "Inside Quiz service getQuizBasicInfo() with param Long: " + quizId);

        QuizDTO quiz = getQuiz(quizId);
        QuizBasicInfo quizBasicInfo = new QuizBasicInfo();
        quizBasicInfo.setQuizId(quiz.getId());
        quizBasicInfo.setQuizDescription(quiz.getDescription());
        quizBasicInfo.setStatus(quiz.getStatus());
        quizBasicInfo.setQuizName(quiz.getTitle());
        System.out.println(quizBasicInfo.toString()+"Sent form method  getQuizBasicInfo");

        logger.info( "Inside Quiz service exiting getQuizBasicInfo() with value QuizBasicInfo: " + quizBasicInfo.toString());

        return quizBasicInfo;
    }
    private QuizDTO convertToQuizDTO(Quiz q) {
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
}
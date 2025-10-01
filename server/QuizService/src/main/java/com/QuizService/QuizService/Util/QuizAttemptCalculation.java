package com.QuizService.QuizService.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.QuizService.QuizService.DTO.QuizAttemptRequestDTO;
import com.QuizService.QuizService.DTO.QuizResponseDTO;
import com.QuizService.QuizService.DTO.QuizWithQuestions;

import com.QuizService.QuizService.DTO.*;

@Service
public class QuizAttemptCalculation {
    public QuizAttemptRequestDTO calculateAttempt(QuizWithQuestions quiz, QuizResponseDTO quizResponseDTO, String uniqueKey) throws Exception{
        QuizAttemptRequestDTO quizAttemptRequestDTO = new QuizAttemptRequestDTO();
        quizAttemptRequestDTO.setUserId(quizResponseDTO.getUserId());
        quizAttemptRequestDTO.setQuizId(quizResponseDTO.getQuizId());
        quizAttemptRequestDTO.setUniqueKey(uniqueKey);
        quizAttemptRequestDTO.setStartedAt(quizResponseDTO.getStartedAt());
        quizAttemptRequestDTO.setCompletedAt(quizResponseDTO.getStartedAt());
        quizAttemptRequestDTO.setAttemptStatus("COMPLETED");
        quizAttemptRequestDTO.setMaxScore(quiz.getQuiz().getTotalMarks());
        quizAttemptRequestDTO.setTotalQuestions(quiz.getQuestion().size());
        quizAttemptRequestDTO.setQuestionAnswersList(new ArrayList<>());

        List<QuestionDTO> qListFromDB = quiz.getQuestion();
        Map<String,String> qContent = new HashMap<>();
        Map<String,Double> marksOfQuestion = new HashMap<>();
        int correctAns = 0, incorrectAnswers = 0, totalQuestions = 0;
        double marksObtained = 0.0;

        for(QuestionDTO q : qListFromDB){
            qContent.put(q.getContent(), q.getCorrectAns());
            marksOfQuestion.put(q.getContent(), q.getMarks());
        }

        for (Pair QnA : quizResponseDTO.getListOfUserAttemptQuestionsAndAnswers()) {
            if(!qContent.containsKey(QnA.getContent())){
                continue;
            }

            if(QnA.getSelectedAnswer().equals(qContent.get(QnA.getContent()))){
                correctAns += 1;
                marksObtained += marksOfQuestion.get(QnA.getContent());
            }
            else if(!QnA.getSelectedAnswer().equals(qContent.get(QnA.getContent()))){
                incorrectAnswers += 1;
            }

            QuestionAnswer qAnswer = new QuestionAnswer(QnA.getContent(), QnA.getSelectedAnswer(), qContent.get(QnA.getContent()));
            quizAttemptRequestDTO.getQuestionAnswersList().add(qAnswer);
        }

        totalQuestions = quiz.getQuestion().size();
        
        quizAttemptRequestDTO.setCorrectAnswers(correctAns);
        quizAttemptRequestDTO.setIncorrectAnswers(incorrectAnswers);
        quizAttemptRequestDTO.setUnanswered(totalQuestions - quizResponseDTO.getListOfUserAttemptQuestionsAndAnswers().size());
        quizAttemptRequestDTO.setTotalQuestions(totalQuestions);
        quizAttemptRequestDTO.setScore(marksObtained);
        quizAttemptRequestDTO.setPercentage((marksObtained/quizAttemptRequestDTO.getMaxScore()*100.0));
        quizAttemptRequestDTO.setTimeTaken(quizAttemptRequestDTO.getStartedAt(), quizAttemptRequestDTO.getCompletedAt());

        return quizAttemptRequestDTO;
    }
}

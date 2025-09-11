package com.QuizService.QuizService.Service;

import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Exception.NotFoundException;
import com.QuizService.QuizService.Repository.QuizRepo;
import com.QuizService.QuizService.Util.QuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    private QuizMapper quizMapper;

    public QuizDTO getQuiz(Long id){
        Quiz q = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found"));

        return quizMapper.toDto(q);
    }

    public String deleteQuiz(Long id){
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + id));
        quizRepo.delete(quiz);

        return "Quiz deleted";
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

    public QuizDTO addQuiz(Quiz quiz){
        return quizMapper.toDto(quizRepo.save(quiz));
    }

    public QuizDTO updateQuiz(Long id, Quiz quiz){
        Quiz q = quizRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz does not exist"));

        if(quiz.getIsPrev() != null){
            q.setIsPrev(quiz.getIsPrev());
        }

        if(quiz.getCategory() != null){
            q.setCategory(quiz.getCategory());
        }

        if(quiz.getQuestionAnsPair() != null && !quiz.getQuestionAnsPair().isEmpty()){
            q.setQuestionAnsPair(quiz.getQuestionAnsPair());
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

        return quizMapper.toDto(q);
    }

    public List<QuizDTO> getQuizByCategory(String category){
        return quizRepo.findByCategory(category);
    }

    public List<QuizDTO> getQuizBySubject(String subject){
        return quizRepo.findBySubject(subject);
    }

    public List<QuizDTO> getByPrev(Boolean isPrev, String cat){
        return quizRepo.findByPrev(isPrev, cat);
    }
}

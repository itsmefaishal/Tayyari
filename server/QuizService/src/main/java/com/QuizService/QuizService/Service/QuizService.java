package com.QuizService.QuizService.Service;

import com.QuizService.QuizService.DTO.QuizDTO;
import com.QuizService.QuizService.Entity.Quiz;
import com.QuizService.QuizService.Repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    public Optional<Quiz> getQuiz(Long id){
        try{
            return quizRepo.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteQuiz(Long id){
        try{
            quizRepo.deleteById(id);

            return "Quiz deleted";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Quiz> addQuiz(Quiz quiz){
        try{
            return Optional.of(quizRepo.save(quiz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Quiz> updateQuiz(Long id, Quiz quiz){
        try{
            Optional<Quiz> qz = quizRepo.findById(id);
            Quiz q = qz.get();

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

            return Optional.of(q);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Quiz>> getQuizByCategory(String category){
        try{
            return Optional.of(quizRepo.findByCategory(category));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Quiz>> getQuizBySubject(String subject){
        try{
            return Optional.of(quizRepo.findBySubject(subject));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Quiz>> getByPrev(Boolean isPrev, String cat){
        try{
            return Optional.of(quizRepo.findByPrev(isPrev, cat));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

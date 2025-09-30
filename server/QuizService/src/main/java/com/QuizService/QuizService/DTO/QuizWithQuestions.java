package com.QuizService.QuizService.DTO;
import java.util.List;

public class QuizWithQuestions {

    private QuizDTO quiz;
    private List<QuestionDTO> question;

    public QuizWithQuestions(QuizDTO quiz, List<QuestionDTO> question) {
        this.question = question;
        this.quiz = quiz;
    }

    public QuizWithQuestions() {
    }

    public QuizDTO getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizDTO quiz) {
        this.quiz = quiz;
    }

    public List<QuestionDTO> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionDTO> question) {
        this.question = question;
    }
}

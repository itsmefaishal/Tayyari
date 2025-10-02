package com.QuizService.QuizService.DTO;
import java.time.LocalDateTime;
import java.util.List;

public class QuizResponseDTO {

    private long quizAttemptId;
    private long userId;
    private long quizId;
    private long enrollementId;
    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
    private List<Pair> listOfUserAttemptQuestionsAndAnswers;

   
    public List<Pair> getListOfUserAttemptQuestionsAndAnswers() {
        return listOfUserAttemptQuestionsAndAnswers;
    }
    public void setListOfUserAttemptQuestionsAndAnswers(List<Pair> listOfUserAttemptQuestionsAndAnswers) {
        this.listOfUserAttemptQuestionsAndAnswers = listOfUserAttemptQuestionsAndAnswers;
    }
    public long getQuizAttemptId() {
        return quizAttemptId;
    }
    public void setQuizAttemptId(long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getQuizId() {
        return quizId;
    }
    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }
    public long getEnrollementId() {
        return enrollementId;
    }
    public void setEnrollementId(long enrollementId) {
        this.enrollementId = enrollementId;
    }
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    public QuizResponseDTO() {
    }
	
	 @Override
    public String toString() {
        return "QuizResponseDTO [quizAttemptId=" + quizAttemptId + ", userId=" + userId + ", quizId=" + quizId
                + ", enrollementId=" + enrollementId + ", startedAt=" + startedAt + ", submittedAt=" + submittedAt
                + ", listOfUserAttemptQuestionsAndAnswers=" + listOfUserAttemptQuestionsAndAnswers + "]";
    }
}

package com.QuizService.QuizService.DTO;
import java.util.List;
import java.time.LocalDateTime;

public class QuizAttemptRequestDTO {
    private long userId;
    private long quizId;
    private String uniqueKey;
    private Long enrollmentId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String attemptStatus;
    private double score;
    private double maxScore;
    private double percentage;
    private int timeTaken;
    private int correctAnswers;
    private int incorrectAnswers;
    private int unanswered;
    private int totalQuestions;
    private List<QuestionAnswer> questionAnswersList;
    
    public Long getEnrollmentId() {
        return enrollmentId;
    }
    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
    
    @Override
    public String toString() {
        return "QuizAttemptRequestDTO [userId=" + userId + ", quizId=" + quizId + ", uniqueKey=" + uniqueKey
                + ", enrollmentId=" + enrollmentId + ", startedAt=" + startedAt + ", completedAt=" + completedAt
                + ", attemptStatus=" + attemptStatus + ", score=" + score + ", maxScore=" + maxScore + ", percentage="
                + percentage + ", timeTaken=" + timeTaken + ", correctAnswers=" + correctAnswers + ", incorrectAnswers="
                + incorrectAnswers + ", unanswered=" + unanswered + ", totalQuestions=" + totalQuestions
                + ", questionAnswersList=" + questionAnswersList + "]";
    }
    public String getUniqueKey() {
        return uniqueKey;
    }
    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
    public List<QuestionAnswer> getQuestionAnswersList() {
        return questionAnswersList;
    }
    public void setQuestionAnswersList(List<QuestionAnswer> questionAnswersList) {
        this.questionAnswersList = questionAnswersList;
    }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getQuizId() { return quizId; }
    public void setQuizId(long quizId) { this.quizId = quizId; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public String getAttemptStatus() { return attemptStatus; }
    public void setAttemptStatus(String attemptStatus) { this.attemptStatus = attemptStatus; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public double getMaxScore() { return maxScore; }
    public void setMaxScore(double maxScore) { this.maxScore = maxScore; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public int getTimeTaken() { return timeTaken; }
    public void setTimeTaken(int timeTook){
        this.timeTaken = timeTook;
    }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getIncorrectAnswers() { return incorrectAnswers; }
    public void setIncorrectAnswers(int incorrectAnswers) { this.incorrectAnswers = incorrectAnswers; }

    public int getUnanswered() { return unanswered; }
    public void setUnanswered(int unanswered) { this.unanswered = unanswered; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public List<QuestionAnswer> getAnswers() { return questionAnswersList; }
    public void setAnswers(List<QuestionAnswer> qaList) { this.questionAnswersList = qaList; }
}

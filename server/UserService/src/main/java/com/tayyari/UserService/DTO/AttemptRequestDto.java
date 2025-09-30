package com.tayyari.UserService.DTO;

import com.tayyari.UserService.ENUMS.AttemptStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttemptRequestDto {
    @NotNull(message = "Quiz ID is required")
    private Long quizId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Started at timestamp is required")
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @NotNull(message = "Status is required")
    private AttemptStatus status;

    @DecimalMin(value = "0.0", message = "Score must be non-negative")
    private BigDecimal score;

    @DecimalMin(value = "0.0", message = "Max score must be non-negative")
    private BigDecimal maxScore;

    @Min(value = 0, message = "Time taken must be non-negative")
    private Integer timeTaken;

    @Min(value = 0, message = "Correct answers must be non-negative")
    private Integer correctAnswers = 0;

    @Min(value = 0, message = "Incorrect answers must be non-negative")
    private Integer incorrectAnswers = 0;

    @Min(value = 0, message = "Unanswered must be non-negative")
    private Integer unanswered = 0;

    @Min(value = 1, message = "Total questions must be at least 1")
    private Integer totalQuestions;
    private  double percentage;

    private List<AnswerDetailDto> answerDetails;

    public AttemptRequestDto() {
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void setStatus(AttemptStatus status) {
        this.status = status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Integer incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public Integer getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(Integer unanswered) {
        this.unanswered = unanswered;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<AnswerDetailDto> getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(List<AnswerDetailDto> answerDetails) {
        this.answerDetails = answerDetails;
    }
}

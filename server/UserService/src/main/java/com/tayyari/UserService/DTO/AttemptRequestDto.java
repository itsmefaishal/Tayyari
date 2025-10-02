package com.tayyari.UserService.DTO;

import com.tayyari.UserService.ENUMS.AttemptStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttemptRequestDto {
    @NotNull(message = "Quiz ID is required")
	@JsonProperty("quizId")
    private Long quizId;

    @NotNull(message = "User ID is required")
	@JsonProperty("userId")
    private Long userId;

    @JsonProperty("uniqueKey")
    private String uniqueKey;

    @NotNull(message = "Started at timestamp is required")
	@JsonProperty("startedAt")
    private LocalDateTime startedAt;

	@JsonProperty("completedAt")
    private LocalDateTime completedAt;

    @NotNull(message = "Status is required")
	@JsonProperty("attemptStatus")
    private AttemptStatus status;

    @DecimalMin(value = "0.0", message = "Score must be non-negative")
	@JsonProperty("score")
    private BigDecimal score;

    @DecimalMin(value = "0.0", message = "Max score must be non-negative")
	@JsonProperty("maxScore")
    private BigDecimal maxScore;

    @Min(value = 0, message = "Time taken must be non-negative")
	@JsonProperty("timeTaken")
    private Integer timeTaken;

    @Min(value = 0, message = "Correct answers must be non-negative")
	@JsonProperty("correctAnswers")
    private Integer correctAnswers = 0;

    @Min(value = 0, message = "Incorrect answers must be non-negative")
	@JsonProperty("incorrectAnswers")
    private Integer incorrectAnswers = 0;

    @Min(value = 0, message = "Unanswered must be non-negative")
	@JsonProperty("unanswered")
    private Integer unanswered = 0;

    @Min(value = 1, message = "Total questions must be at least 1")
	@JsonProperty("totalQuestions")
    private Integer totalQuestions;
	@JsonProperty("percentage")
    private  double percentage;
	@JsonProperty("questionAnswersList")
    private List<AnswerDetailDto> answerDetails;

    public AttemptRequestDto() {
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
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

    @Override
    public String toString() {
        return "AttemptRequestDto{" +
                "quizId=" + quizId +
                ", userId=" + userId +
                ", uniqueKey='" + uniqueKey + '\'' +
                ", startedAt=" + startedAt +
                ", completedAt=" + completedAt +
                ", status=" + status +
                ", score=" + score +
                ", maxScore=" + maxScore +
                ", timeTaken=" + timeTaken +
                ", correctAnswers=" + correctAnswers +
                ", incorrectAnswers=" + incorrectAnswers +
                ", unanswered=" + unanswered +
                ", totalQuestions=" + totalQuestions +
                ", percentage=" + percentage +
                ", answerDetails=" + answerDetails +
                '}';
    }
}

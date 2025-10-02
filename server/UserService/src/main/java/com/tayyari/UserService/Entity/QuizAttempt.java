package com.tayyari.UserService.Entity;

import com.tayyari.UserService.DTO.AnswerDetailDto;
import com.tayyari.UserService.ENUMS.AttemptStatus;
import jakarta.persistence.*;


import org.hibernate.annotations.Type;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import java.time.LocalDateTime;
import java.util.List;




@Entity
@Table(
        name = "quiz_attempts",
        indexes = {
                @Index(name = "idx_user_quiz", columnList = "user_id, quiz_id"),
                @Index(name = "idx_completed_at", columnList = "completed_at")
        }
)
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QuizAttemptId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserEnrollmentId")
    private UserEnrollment enrollment;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    @Column(name ="unique_key", nullable = false,unique = true)
    private String uniqueKey;

    @Column
    private AttemptStatus attemptStatus = AttemptStatus.IN_PROGRESS;

    @Column(name = "score")
    private double score;

    @Column(name = "max_score")
    private double maxScore;

    @Column(name = "percentage")
    private double percentage;

    @Column(name = "time_taken")
    private Integer timeTaken; // in seconds

    @Column(name = "correct_answers")
    private Integer correctAnswers = 0;

    @Column(name = "incorrect_answers")
    private Integer incorrectAnswers = 0;

    @Column(name = "unanswered")
    private Integer unanswered = 0;

    @Column(name = "total_questions")
    private Integer totalQuestions;


    @Type(JsonBinaryType.class)
    @Column(name = "answer_details", columnDefinition = "jsonb")
    private List<AnswerDetailDto> answers;




    public QuizAttempt() {
    }

    public Long getQuizAttemptId() {
        return QuizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        QuizAttemptId = quizAttemptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public UserEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(UserEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public AttemptStatus getAttemptStatus() {
        return attemptStatus;
    }

    public void setAttemptStatus(AttemptStatus attemptStatus) {
        this.attemptStatus = attemptStatus;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
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

    public List<AnswerDetailDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDetailDto> answers) {
        this.answers = answers;
    }
}

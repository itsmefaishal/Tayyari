package com.tayyari.UserService.Entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "user_enrollments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "quiz_id"})
        }
)
public class UserEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userEnrollmentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @Column(name = "exam_category")
    private String examCategory;

    @Column(name = "enrolled_at")
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    @Column(name = "priority_level")
    private Integer priorityLevel = 1;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enum for status
    public enum EnrollmentStatus {
        ACTIVE("active"),
        COMPLETED("completed"),
        DROPPED("dropped"),
        EXPIRED("expired");

        private final String value;
        EnrollmentStatus(String value)
        {
            this.value=value;
        }

        public String getValue()
        {
            return value;
        }
    }

    public UserEnrollment() {
    }

    public Long getUserEnrollmentId() {
        return userEnrollmentId;
    }

    public void setUserEnrollmentId(Long userEnrollmentId) {
        this.userEnrollmentId = userEnrollmentId;
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

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public EnrollmentStatus getEnrollStatus() {
        return status;
    }

    public void setEnrollStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public LocalDate getTargetCompletionDate() {
        return targetCompletionDate;
    }

    public void setTargetCompletionDate(LocalDate targetCompletionDate) {
        this.targetCompletionDate = targetCompletionDate;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package com.tayyari.UserService.DTO;

import com.tayyari.UserService.Entity.UserEnrollment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnrollmentResponseDto {

    private Long enrollmentId;
    private Long userId;
    private Long quizId;
    private String examCategory;
    private LocalDateTime enrolledAt;
    private UserEnrollment.EnrollmentStatus status;
//    private LocalDate targetCompletionDate;
//    private Integer priorityLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    // Additional quiz information from QuizService
       private QuizBasicInfoDto quizInfo;

    public EnrollmentResponseDto(Long enrollmentId, Long userId, Long quizId, String examCategory, LocalDateTime enrolledAt, UserEnrollment.EnrollmentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, QuizBasicInfoDto quizInfo) {
        this.enrollmentId = enrollmentId;
        this.userId = userId;
        this.quizId = quizId;
        this.examCategory = examCategory;
        this.enrolledAt = enrolledAt;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quizInfo = quizInfo;
    }

    public EnrollmentResponseDto() {
    }

    public QuizBasicInfoDto getQuizInfo() {
        return quizInfo;
    }

    public void setQuizInfo(QuizBasicInfoDto quizInfo) {
        this.quizInfo = quizInfo;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
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

    public UserEnrollment.EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(UserEnrollment.EnrollmentStatus status) {
        this.status = status;
    }

//    public LocalDate getTargetCompletionDate() {
//        return targetCompletionDate;
//    }
//
//    public void setTargetCompletionDate(LocalDate targetCompletionDate) {
//        this.targetCompletionDate = targetCompletionDate;
//    }
//
//    public Integer getPriorityLevel() {
//        return priorityLevel;
//    }
//
//    public void setPriorityLevel(Integer priorityLevel) {
//        this.priorityLevel = priorityLevel;
//    }

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

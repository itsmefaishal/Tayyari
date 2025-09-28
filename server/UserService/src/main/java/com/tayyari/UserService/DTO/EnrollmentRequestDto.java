package com.tayyari.UserService.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EnrollmentRequestDto {

    @NotNull(message = "Quiz ID is required")
    private Long quizId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @Size(max = 100, message = "Exam category must not exceed 100 characters")
    private String examCategory;

    public EnrollmentRequestDto() {
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }
}

package com.tayyari.UserService.DTO;

public class QuizBasicInfoDto {
    private Long quizId;
    private String quizName;
    private String quizDescription;
    private String status;


    public QuizBasicInfoDto() {
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status=status;

    }
}

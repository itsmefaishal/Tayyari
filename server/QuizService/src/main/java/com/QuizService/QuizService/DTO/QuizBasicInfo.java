package com.QuizService.QuizService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizBasicInfo {
   private Long quizId;
    private String quizName;
    private String quizDescription;
    @JsonProperty("status")
    private String status;

    public QuizBasicInfo() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QuizBasicInfo{" +
                "quizId=" + quizId +
                ", quizName='" + quizName + '\'' +
                ", quizDescription='" + quizDescription + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

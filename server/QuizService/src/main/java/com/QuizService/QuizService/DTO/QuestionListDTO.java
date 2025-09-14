package com.QuizService.QuizService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestionListDTO {
    @JsonProperty("ids")
    private List<Long> ids;

    public QuestionListDTO() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}


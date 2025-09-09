package com.questionService.questions.QuestionDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuestionDTO {
    @JsonProperty("questionContent")
    private String QuestionContent;
    private String correctAns;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String subCat;
    @JsonProperty("subject")
    private String subject;
    private String category;
    private Boolean multipleChoice;
    private String imageUrl;
    private int marks;
    private int negativeMarks;
    private String difficulty;

}

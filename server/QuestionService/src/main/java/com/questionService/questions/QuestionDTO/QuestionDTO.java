package com.questionService.questions.QuestionDTO;

import lombok.Data;

@Data
public class QuestionDTO {
    private String QuestionContent;
    private String correctAns;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String subject;
    private String category;
    private String subCat;
    private Boolean multipleChoice;
    private String imageUrl;
    private int marks;
    private int negativeMarks;
    private String difficulty;


}

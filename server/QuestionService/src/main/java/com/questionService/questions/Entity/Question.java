package com.questionService.questions.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @JsonProperty("questionContent")
    private String QuestionContent;
    private String correctAns;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String category;
    private String subCat;
    private String subject;
    private Boolean multipleChoice;
    private String imageUrl;
    private int marks;
    private int negativeMarks;
    private String difficulty;
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;

}

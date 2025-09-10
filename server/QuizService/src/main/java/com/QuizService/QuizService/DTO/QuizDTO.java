package com.QuizService.QuizService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private long id;
    private String title;
    private String description;
    private int duration;
    private double totalMarks;
    private double passMarks;
    private String subject;
    private boolean isPrev;
    private String category;
    private HashMap<Integer, String> questionAnsPair;
}

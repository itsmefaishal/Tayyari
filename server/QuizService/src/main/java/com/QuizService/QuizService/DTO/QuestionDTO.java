package com.QuizService.QuizService.DTO;


public class QuestionDTO {

    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAns;
    private double marks;

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    @Override
    public String toString() {
        return "QuestionDTO [content=" + content + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC="
                + optionC + ", optionD=" + optionD + ", correctAns=" + correctAns + ", marks=" + marks + "]";
    }

    
}

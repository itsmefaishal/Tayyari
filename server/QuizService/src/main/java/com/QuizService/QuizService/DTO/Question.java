package com.QuizService.QuizService.DTO;


import java.time.LocalDate;
import java.util.List;

public class Question {
    private long Id;
    private String QuestionContent;
    private String correctAns;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String subject;
    private String subCat;
    private Boolean multipleChoice;
    private String imageUrl;
    private int marks;
    private int negativeMarks;
    private String difficulty;
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private String optionFour;
    private List<String> category;

        @Override
    public String toString() {
        return "Question [Id=" + Id + ", QuestionContent=" + QuestionContent + ", correctAns=" + correctAns
                + ", optionOne=" + optionOne + ", optionTwo=" + optionTwo + ", optionThree=" + optionThree
                + ", subject=" + subject + ", subCat=" + subCat + ", multipleChoice=" + multipleChoice + ", imageUrl="
                + imageUrl + ", marks=" + marks + ", negativeMarks=" + negativeMarks + ", difficulty=" + difficulty
                + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", optionFour="
                + optionFour + ", category=" + category + ", getId()=" + getId() + ", getQuestionContent()="
                + getQuestionContent() + ", getCorrectAns()=" + getCorrectAns() + ", getOptionOne()=" + getOptionOne()
                + ", getOptionTwo()=" + getOptionTwo() + ", getOptionThree()=" + getOptionThree() + ", getOptionFour()="
                + getOptionFour() + ", getCategory()=" + getCategory() + ", getSubject()=" + getSubject()
                + ", getSubCat()=" + getSubCat() + ", getMultipleChoice()=" + getMultipleChoice() + ", getImageUrl()="
                + getImageUrl() + ", getMarks()=" + getMarks() + ", getNegativeMarks()=" + getNegativeMarks()
                + ", getDifficulty()=" + getDifficulty() + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedAt()="
                + getCreatedAt() + ", getUpdatedBy()=" + getUpdatedBy() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

    public Question() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getQuestionContent() {
        return QuestionContent;
    }

    public void setQuestionContent(String questionContent) {
        QuestionContent = questionContent;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public Boolean getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(Boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(int negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }



}


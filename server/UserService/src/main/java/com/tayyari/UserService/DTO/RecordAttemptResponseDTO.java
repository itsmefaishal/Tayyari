package com.tayyari.UserService.DTO;

public class RecordAttemptResponseDTO {

    private String uniqueKey;
    private double score;
    private double maxScore;
    private int timeTaken;
    private int correctAnswers;
    private int incorrectAnswers;
    private int unanswered;

    public RecordAttemptResponseDTO() {
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(int unanswered) {
        this.unanswered = unanswered;
    }

    @Override
    public String toString() {
        return "RecordAttemptResponseDTO{" +
                "uniqueKey='" + uniqueKey + '\'' +
                ", score=" + score +
                ", maxScore=" + maxScore +
                ", timeTaken=" + timeTaken +
                ", correctAnswers=" + correctAnswers +
                ", incorrectAnswers=" + incorrectAnswers +
                ", unanswered=" + unanswered +
                '}';
    }
}

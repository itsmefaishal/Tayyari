package com.tayyari.UserService.Entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "exam_preparation")
public class ExamPreparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "Long")
    private Long userId;

    @Column(name = "exam_type", nullable = false, length = 100)
    private String examType;

    @Column(name = "target_exam_date")
    private LocalDate targetExamDate;

    @Column(name = "preparation_start_date")
    private LocalDate preparationStartDate;

    @Column(name = "total_subjects")
    private Integer totalSubjects;

    @Column(name = "subjects_covered", nullable = false)
    private Integer subjectsCovered = 0;

    @Column(name = "total_quizzes_planned")
    private Integer totalQuizzesPlanned;

    @Column(name = "quizzes_completed", nullable = false)
    private Integer quizzesCompleted = 0;

    @Column(name = "average_performance", precision = 5, scale = 2)
    private BigDecimal averagePerformance = BigDecimal.ZERO;

    // JSONB fields can be mapped as String or List<String> with converters
    @Column(name = "weak_subjects", columnDefinition = "jsonb")
    private String weakSubjects; // store raw JSON string

    @Column(name = "strong_subjects", columnDefinition = "jsonb")
    private String strongSubjects; // store raw JSON string

    @Column(name = "daily_target_hours", nullable = false)
    private Integer dailyTargetHours = 2;

    @Column(name = "actual_daily_hours", precision = 4, scale = 2)
    private BigDecimal actualDailyHours = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status = Status.active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Status {
        planning,
        active,
        completed,
        paused
    }

    public ExamPreparation() {
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public LocalDate getTargetExamDate() { return targetExamDate; }
    public void setTargetExamDate(LocalDate targetExamDate) { this.targetExamDate = targetExamDate; }

    public LocalDate getPreparationStartDate() { return preparationStartDate; }
    public void setPreparationStartDate(LocalDate preparationStartDate) { this.preparationStartDate = preparationStartDate; }

    public Integer getTotalSubjects() { return totalSubjects; }
    public void setTotalSubjects(Integer totalSubjects) { this.totalSubjects = totalSubjects; }

    public Integer getSubjectsCovered() { return subjectsCovered; }
    public void setSubjectsCovered(Integer subjectsCovered) { this.subjectsCovered = subjectsCovered; }

    public Integer getTotalQuizzesPlanned() { return totalQuizzesPlanned; }
    public void setTotalQuizzesPlanned(Integer totalQuizzesPlanned) { this.totalQuizzesPlanned = totalQuizzesPlanned; }

    public Integer getQuizzesCompleted() { return quizzesCompleted; }
    public void setQuizzesCompleted(Integer quizzesCompleted) { this.quizzesCompleted = quizzesCompleted; }

    public BigDecimal getAveragePerformance() { return averagePerformance; }
    public void setAveragePerformance(BigDecimal averagePerformance) { this.averagePerformance = averagePerformance; }

    public String getWeakSubjects() { return weakSubjects; }
    public void setWeakSubjects(String weakSubjects) { this.weakSubjects = weakSubjects; }

    public String getStrongSubjects() { return strongSubjects; }
    public void setStrongSubjects(String strongSubjects) { this.strongSubjects = strongSubjects; }

    public Integer getDailyTargetHours() { return dailyTargetHours; }
    public void setDailyTargetHours(Integer dailyTargetHours) { this.dailyTargetHours = dailyTargetHours; }

    public BigDecimal getActualDailyHours() { return actualDailyHours; }
    public void setActualDailyHours(BigDecimal actualDailyHours) { this.actualDailyHours = actualDailyHours; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

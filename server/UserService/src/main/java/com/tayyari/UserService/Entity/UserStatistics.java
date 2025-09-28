package com.tayyari.UserService.Entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, columnDefinition = "Long")
    private Long userId;

    @Column(name = "total_enrollments", nullable = false)
    private Integer totalEnrollments = 0;

    @Column(name = "active_enrollments", nullable = false)
    private Integer activeEnrollments = 0;

    @Column(name = "completed_quizzes", nullable = false)
    private Integer completedQuizzes = 0;

    @Column(name = "total_attempts", nullable = false)
    private Integer totalAttempts = 0;

    @Column(name = "average_score", precision = 5, scale = 2)
    private BigDecimal averageScore = BigDecimal.ZERO;

    @Column(name = "best_score", precision = 5, scale = 2)
    private BigDecimal bestScore = BigDecimal.ZERO;

    @Column(name = "total_time_spent", nullable = false)
    private Integer totalTimeSpent = 0; // in seconds

    @Column(name = "current_streak", nullable = false)
    private Integer currentStreak = 0;

    @Column(name = "longest_streak", nullable = false)
    private Integer longestStreak = 0;

    @Column(name = "overall_rank")
    private Integer overallRank;

    @Column(name = "state_rank")
    private Integer stateRank;

    @Column(name = "last_activity_at")
    private LocalDateTime lastActivityAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserStatistics() {
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getTotalEnrollments() { return totalEnrollments; }
    public void setTotalEnrollments(Integer totalEnrollments) { this.totalEnrollments = totalEnrollments; }

    public Integer getActiveEnrollments() { return activeEnrollments; }
    public void setActiveEnrollments(Integer activeEnrollments) { this.activeEnrollments = activeEnrollments; }

    public Integer getCompletedQuizzes() { return completedQuizzes; }
    public void setCompletedQuizzes(Integer completedQuizzes) { this.completedQuizzes = completedQuizzes; }

    public Integer getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Integer totalAttempts) { this.totalAttempts = totalAttempts; }

    public BigDecimal getAverageScore() { return averageScore; }
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }

    public BigDecimal getBestScore() { return bestScore; }
    public void setBestScore(BigDecimal bestScore) { this.bestScore = bestScore; }

    public Integer getTotalTimeSpent() { return totalTimeSpent; }
    public void setTotalTimeSpent(Integer totalTimeSpent) { this.totalTimeSpent = totalTimeSpent; }

    public Integer getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(Integer currentStreak) { this.currentStreak = currentStreak; }

    public Integer getLongestStreak() { return longestStreak; }
    public void setLongestStreak(Integer longestStreak) { this.longestStreak = longestStreak; }

    public Integer getOverallRank() { return overallRank; }
    public void setOverallRank(Integer overallRank) { this.overallRank = overallRank; }

    public Integer getStateRank() { return stateRank; }
    public void setStateRank(Integer stateRank) { this.stateRank = stateRank; }

    public LocalDateTime getLastActivityAt() { return lastActivityAt; }
    public void setLastActivityAt(LocalDateTime lastActivityAt) { this.lastActivityAt = lastActivityAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

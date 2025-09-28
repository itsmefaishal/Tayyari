package com.tayyari.UserService.Entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "user_rankings",
        indexes = {
                @Index(name = "idx_user_ranking_type", columnList = "user_id, ranking_type"),
                @Index(name = "idx_ranking_date", columnList = "ranking_date")
        }
)
public class UserRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "UUID")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ranking_type", nullable = false, length = 20)
    private RankingType rankingType;

    @Column(name = "entity_id", length = 100)
    private String entityId;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;

    @Column(name = "total_participants")
    private Integer totalParticipants;

    @Column(name = "score", precision = 10, scale = 2)
    private BigDecimal score;

    @Column(name = "percentile", precision = 5, scale = 2)
    private BigDecimal percentile;

    @Column(name = "ranking_date", nullable = false)
    private LocalDate rankingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum RankingType {
        overall,
        quiz_wise,
        state_wise,
        category_wise
    }

    public UserRanking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RankingType getRankingType() {
        return rankingType;
    }

    public void setRankingType(RankingType rankingType) {
        this.rankingType = rankingType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public Integer getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(Integer totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPercentile() {
        return percentile;
    }

    public void setPercentile(BigDecimal percentile) {
        this.percentile = percentile;
    }

    public LocalDate getRankingDate() {
        return rankingDate;
    }

    public void setRankingDate(LocalDate rankingDate) {
        this.rankingDate = rankingDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

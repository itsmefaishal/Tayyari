package com.tayyari.UserService.DTO;

import com.tayyari.UserService.Entity.UserProfile;

import java.util.List;

public class DashboardResponseDto {
    private Long userId;
//    private DashboardSummaryDto summary;
    private List<EnrollmentResponseDto> activeEnrollments;
    private List<AttemptResponseDto> recentAttempts;
//    private List<RankingResponseDto> rankings;
    private List<ProgressResponseDto> topProgress;
//    private ExamPreparationDto examPreparation;
//    private PerformanceTrendDto performanceTrend;
    private UserProfile userProfile;

    public DashboardResponseDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<EnrollmentResponseDto> getActiveEnrollments() {
        return activeEnrollments;
    }

    public void setActiveEnrollments(List<EnrollmentResponseDto> activeEnrollments) {
        this.activeEnrollments = activeEnrollments;
    }

    public List<AttemptResponseDto> getRecentAttempts() {
        return recentAttempts;
    }

    public void setRecentAttempts(List<AttemptResponseDto> recentAttempts) {
        this.recentAttempts = recentAttempts;
    }

    public List<ProgressResponseDto> getTopProgress() {
        return topProgress;
    }

    public void setTopProgress(List<ProgressResponseDto> topProgress) {
        this.topProgress = topProgress;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}

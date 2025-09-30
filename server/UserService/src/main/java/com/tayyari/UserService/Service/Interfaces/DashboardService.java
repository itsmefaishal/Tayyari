package com.tayyari.UserService.Service.Interfaces;

import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.DTO.DashboardResponseDto;
import com.tayyari.UserService.DTO.EnrollmentResponseDto;
import com.tayyari.UserService.DTO.ProgressResponseDto;
import com.tayyari.UserService.Entity.UserProfile;

import java.util.List;

public interface DashboardService {


    public DashboardResponseDto LoadDashboard(Long userId);
//    private Long userId;
//    //    private DashboardSummaryDto summary;
//    private List<EnrollmentResponseDto> activeEnrollments;
//    private List<AttemptResponseDto> recentAttempts;
//    //    private List<RankingResponseDto> rankings;
//    private List<ProgressResponseDto> topProgress;
//    //    private ExamPreparationDto examPreparation;
////    private PerformanceTrendDto performanceTrend;
//    private UserProfile userProfile;
}

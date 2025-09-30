package com.tayyari.UserService.Service;

import com.tayyari.UserService.Controller.DashboardController;
import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.DTO.DashboardResponseDto;
import com.tayyari.UserService.DTO.EnrollmentResponseDto;
import com.tayyari.UserService.DTO.ProgressResponseDto;
import com.tayyari.UserService.Entity.UserEnrollment;
import com.tayyari.UserService.Entity.UserProfile;
import com.tayyari.UserService.Service.Interfaces.DashboardService;
import com.tayyari.UserService.Service.Interfaces.EnrollmentService;
import com.tayyari.UserService.Service.Interfaces.ProgressTrackingService;
import com.tayyari.UserService.Service.Interfaces.UserProfileService;
import org.apache.logging.log4j.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
   private EnrollmentService enrollmentService;
    @Autowired
   private ProgressTrackingService progressTrackingService;
    @Autowired
    private UserProfileService userProfileService;
    
    
    @Override
    public DashboardResponseDto LoadDashboard(Long userId) {
        logger.info("Inside load Dashboard with user id : {}",userId);
        DashboardResponseDto drd= new DashboardResponseDto();
        List<EnrollmentResponseDto> userEnrollments = enrollmentService.getUserEnrollments(userId, UserEnrollment.EnrollmentStatus.ACTIVE);
        UserProfile user = userProfileService.getUser(userId);
        drd.setActiveEnrollments(userEnrollments);
        drd.setUserId(userId);
        drd.setUserProfile(user);

//        private Long userId;
//        private List<EnrollmentResponseDto> activeEnrollments;
//        private List<ProgressResponseDto> topProgress;
//        private UserProfile userProfile;
        logger.info("Exiting from method LoadDashboard with dashboardResponse {} ",drd);
        return drd;
    }
}

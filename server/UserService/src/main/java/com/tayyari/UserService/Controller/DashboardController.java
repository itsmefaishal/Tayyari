package com.tayyari.UserService.Controller;

import com.tayyari.UserService.DTO.ApiResponse;
import com.tayyari.UserService.DTO.DashboardResponseDto;
import com.tayyari.UserService.Service.Interfaces.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/test")
    public ResponseEntity<String> testPoint(){
        return ResponseEntity.ok("User Service Working !!!");
    }

    @GetMapping("user-dashboard")
    public ApiResponse<DashboardResponseDto> loadDashboardForUser(Long userId)
    {
        logger.info("Entering into DashboardController loadDashboardForUser method with user id: {} :",userId);


            DashboardResponseDto dashboardResponse = dashboardService.LoadDashboard(userId);
            logger.info("Exiting from method with DashboardResponse : {}",dashboardResponse);
            return new ApiResponse<>(true,"Dashboard loaded successfully",dashboardResponse);

    }
}

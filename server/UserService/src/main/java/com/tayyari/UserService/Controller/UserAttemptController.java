package com.tayyari.UserService.Controller;


import com.tayyari.UserService.DTO.ApiResponse;
import com.tayyari.UserService.DTO.AttemptRequestDto;
import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.Service.Interfaces.ProgressTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attempt")
public class UserAttemptController {
    private static final Logger logger = LoggerFactory.getLogger(UserAttemptController.class);
    @Autowired
    ProgressTrackingService progressTracking;
    @PostMapping("/save")
    public ApiResponse<AttemptResponseDto> postAttempt(@RequestBody AttemptRequestDto ard)
    {

        try {
            logger.info("Entering into  postAttempt method with RequestDto: {} :",ard);
            AttemptResponseDto attemptResponse = progressTracking.recordQuizAttempt(ard);
            return  new ApiResponse<>(true,"Attempt posted Successfully",attemptResponse);
        } catch (Exception e) {
            logger.info("catch block  postAttempt method with RequestDto: {} :",ard);
            e.printStackTrace();
            return  new ApiResponse<>(false,"Attempt posting failed",null);

        }
    }
}

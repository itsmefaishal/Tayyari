package com.tayyari.UserService.Controller;


import com.tayyari.UserService.DTO.ApiResponse;
import com.tayyari.UserService.DTO.AttemptRequestDto;
import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.DTO.RecordAttemptResponseDTO;
import com.tayyari.UserService.Entity.QuizAttempt;
import com.tayyari.UserService.Service.Interfaces.ProgressTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAttemptController {
    private static final Logger logger = LoggerFactory.getLogger(UserAttemptController.class);
    @Autowired
    ProgressTrackingService progressTracking;
    @PostMapping("/attempt/save")
    public RecordAttemptResponseDTO postAttempt(@RequestBody AttemptRequestDto ard)
    {

//        try {
            logger.info("Entering into  postAttempt method with RequestDto: {} :",ard);
        RecordAttemptResponseDTO recordAttemptResponse = progressTracking.recordQuizAttempt(ard);
        return  recordAttemptResponse;
//        } catch (Exception e) {
//            logger.info("catch block  postAttempt method with RequestDto: {} :",ard);
//            e.printStackTrace();
//            return  new ApiResponse<>(false,"Attempt posting failed",null);
//
//        }
    }


    @GetMapping("/get-attempts")
    public List<AttemptResponseDto> getAllAttempts(@RequestParam Long userID, @RequestParam Long quizId)
    {
        logger.info("Entering into  Controller getAllAttempts method userID : {}, QuizId : {}",userID,quizId);
        List<AttemptResponseDto> userAttempts = progressTracking.getUserAttempts(userID, quizId);
        logger.info("userAttempts {}",userAttempts);
        return userAttempts;
    }
}

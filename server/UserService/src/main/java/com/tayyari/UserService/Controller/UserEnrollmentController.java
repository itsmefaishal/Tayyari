package com.tayyari.UserService.Controller;


import com.tayyari.UserService.DTO.*;
import com.tayyari.UserService.Service.Interfaces.EnrollmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollment")
public class UserEnrollmentController {

    private static final Logger log = LogManager.getLogger(UserEnrollmentController.class);
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/post")
    public ApiResponse<EnrollmentResponseDto> postEnrollmentForQuiz(@RequestBody EnrollmentRequestDto erd)
    {
        try {
            log.info("inside postEnrollmentForQuiz : {}  ",erd);
            EnrollmentResponseDto savedEnrollment = enrollmentService.enrollUserInQuiz(erd);
            log.info("savedEnrollment : {}  ",savedEnrollment);
            return new ApiResponse<>(true,"Enrollment Posted Successfully",savedEnrollment);
        } catch (Exception e) {
            log.info("Exception : {} ",e);
            return new ApiResponse<>(false,"Enrollment Posting Failed",null);
        }


    }
}

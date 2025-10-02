package com.tayyari.UserService.Service;

import com.tayyari.UserService.DTO.EnrollmentRequestDto;
import com.tayyari.UserService.DTO.EnrollmentResponseDto;
import com.tayyari.UserService.DTO.QuizBasicInfoDto;
import com.tayyari.UserService.ENUMS.ErrorCode;
import com.tayyari.UserService.Entity.UserEnrollment;
import com.tayyari.UserService.Exception.BusinessException;
import com.tayyari.UserService.Integration.AuthServiceClient;
import com.tayyari.UserService.Integration.QuizServiceClient;
import com.tayyari.UserService.Repo.UserEnrollmentRepo;
import com.tayyari.UserService.Service.Interfaces.EnrollmentService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {


    private static final Logger log = LogManager.getLogger(EnrollmentServiceImpl.class);
    @Autowired
    private  UserEnrollmentRepo enrollmentRepo;
    @Autowired
    private  QuizServiceClient quizServiceClient;
    @Autowired
    private  AuthServiceClient authServiceClient;

    @Override
    public EnrollmentResponseDto enrollUserInQuiz(EnrollmentRequestDto request) throws BusinessException {
        log.info("inside enrollUserInQuiz : {}",request.toString());
        // Validate user exists
       // validateUserExists(request.getUserId());

        // Validate quiz exists and is active and get quiz active
        QuizBasicInfoDto quizBasicInfo = validateQuizExists(request.getQuizId());
        log.info("QuizBasicInfoDto : {}",quizBasicInfo);


        // Check if already enrolled
        if (isUserEnrolledInQuiz(request.getUserId(), request.getQuizId())) {
            log.info("inside Check if already enrolled  ");
            throw new BusinessException(ErrorCode.USER_ALREADY_ENROLLED);
        }

        // Create enrollment
        UserEnrollment enrollment = new UserEnrollment();
        enrollment.setUserId(request.getUserId());
        enrollment.setQuizId(request.getQuizId());
        enrollment.setExamCategory(request.getExamCategory());
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setEnrollStatus(UserEnrollment.EnrollmentStatus.ACTIVE);
        enrollment.setCreatedAt(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());
/*EnrollmentResponseDto(Long enrollmentId, Long userId, Long quizId, String examCategory,
LocalDateTime enrolledAt, UserEnrollment.EnrollmentStatus status, LocalDateTime createdAt,
LocalDateTime updatedAt, QuizBasicInfoDto quizInfo) */
        UserEnrollment se = enrollmentRepo.save(enrollment);
        log.info("UserEnrollment after saving : {}",se);
        EnrollmentResponseDto erd = new EnrollmentResponseDto(
                                se.getUserEnrollmentId(),se.getUserId(),se.getQuizId(),se.getExamCategory()
                                ,se.getEnrolledAt(),se.getEnrollStatus(),se.getCreatedAt(),
                                se.getUpdatedAt(),quizBasicInfo );
        log.info("Exiting from method enrollUserInQuiz : {}",erd);
        return erd;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDto> getUserEnrollments(Long userId, UserEnrollment.EnrollmentStatus status) {
        List<UserEnrollment> enrollments;
        log.info("inside  getUserEnrollments with user id : {} and enrollement status {}",userId,status);

        if (status != null) {
            enrollments = enrollmentRepo.findByUserIdAndStatus(userId, status);

        } else {
            enrollments = enrollmentRepo.findByUserId(userId);
        }
        if(enrollments==null)
        {
            throw new BusinessException(ErrorCode.NO_AVAILABLE_ENROLLMENT);
        }
        log.info("Enrollments  : {}",enrollments);

            List<EnrollmentResponseDto> erd = new ArrayList<>();

            for(UserEnrollment enrollment:enrollments)
            {
                log.info("inside for loop enrollment : {}",enrollment);
                //this is a temporary approach will work on the optimization part
                QuizBasicInfoDto quizBasicInfo = validateQuizExists(enrollment.getQuizId());
                EnrollmentResponseDto enrollResponse = new EnrollmentResponseDto(
                        enrollment.getUserEnrollmentId(),enrollment.getUserId(),enrollment.getQuizId(),enrollment.getExamCategory()
                        ,enrollment.getEnrolledAt(),enrollment.getEnrollStatus(),enrollment.getCreatedAt(),
                        enrollment.getUpdatedAt(),quizBasicInfo );

                erd.add(enrollResponse);
            }
        return erd;
    }

    @Override
    public void unenrollFromQuiz(Long userId, Long enrollmentId) {
        log.info("inside unrollFromQuiz : userID {} and enrollments {}",userId,enrollmentId);
        UserEnrollment enrollment = enrollmentRepo.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NO_AVAILABLE_ENROLLMENT));

        if (!enrollment.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        enrollment.setEnrollStatus(UserEnrollment.EnrollmentStatus.DROPPED);
        enrollmentRepo.save(enrollment);



    }

    @Override
    public EnrollmentResponseDto getEnrollment(Long userId, Long enrollmentId) {
        UserEnrollment enrollment = enrollmentRepo.findByUserIdAndUserEnrollmentId(userId, enrollmentId);
        if(enrollment==null)
        {
            throw  new BusinessException(ErrorCode.NO_AVAILABLE_ENROLLMENT);
        }

            //this is a temporary approach will work on the optimization part
            QuizBasicInfoDto quizBasicInfo = validateQuizExists(enrollment.getQuizId());
            EnrollmentResponseDto enrollResponse = new EnrollmentResponseDto(
                    enrollment.getUserEnrollmentId(),enrollment.getUserId(),enrollment.getQuizId(),enrollment.getExamCategory()
                    ,enrollment.getEnrolledAt(),enrollment.getEnrollStatus(),enrollment.getCreatedAt(),
                    enrollment.getUpdatedAt(),quizBasicInfo );

         return enrollResponse;

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserEnrolledInQuiz(Long userId, Long quizId) {
        return enrollmentRepo.findByUserIdAndQuizId(userId, quizId).isPresent();

    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByCategory(String category) {
        List<UserEnrollment> enrollments;
        enrollments=enrollmentRepo.findByExamCategory(category);
        if(enrollments==null)
        {
            throw  new BusinessException(ErrorCode.NO_AVAILABLE_ENROLLMENT);
        }

        List<EnrollmentResponseDto> erd = new ArrayList<>();

        for(UserEnrollment enrollment:enrollments)
        {
            //this is a temporary approach will work on the optimization part
            QuizBasicInfoDto quizBasicInfo = validateQuizExists(enrollment.getQuizId());
            EnrollmentResponseDto enrollResponse = new EnrollmentResponseDto(
                    enrollment.getUserEnrollmentId(),enrollment.getUserId(),enrollment.getQuizId(),enrollment.getExamCategory()
                    ,enrollment.getEnrolledAt(),enrollment.getEnrollStatus(),enrollment.getCreatedAt(),
                    enrollment.getUpdatedAt(),quizBasicInfo );

            erd.add(enrollResponse);
        }
        return erd;
    }




    private void  validateUserExists(Long userid) throws RuntimeException {
        log.info("inside validateUserExists with userid {}",userid);
       if (!authServiceClient.isUserActive(userid)) {
           log.info("inside if block of  validateUserExists");
           throw new RuntimeException("User not found !!");
       }
   }
   private QuizBasicInfoDto validateQuizExists(Long quizId) {
       log.info("inside validateQuizExists with quizId {}",quizId);
       QuizBasicInfoDto basicQuizInfo = quizServiceClient.getBasicQuizInfo(quizId);
        log.info("inside basicQuizInfo  {}",basicQuizInfo);

       if(basicQuizInfo==null)
       {
           log.info("inside basicQuizInfo==null ");
           throw new BusinessException(ErrorCode.QUIZ_NOT_AVAILABLE);
       }
       if(basicQuizInfo.getStatus().isBlank()&&
               basicQuizInfo.getStatus().equalsIgnoreCase("INACTIVE"))
       {
           log.info("inside basicQuizInfo.getStatus().equalsIgnoreCase(INACTIVE)");
           throw new BusinessException(ErrorCode.QUIZ_INACTIVE);
       }
       log.info("Exiting from method  validateQuizExists basicQuizInfo  {}",basicQuizInfo);

       return basicQuizInfo;


   }
   
}

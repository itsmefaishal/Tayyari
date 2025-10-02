package com.tayyari.UserService.Service;

import com.tayyari.UserService.DTO.AttemptRequestDto;
import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.DTO.ProgressResponseDto;
import com.tayyari.UserService.DTO.RecordAttemptResponseDTO;
import com.tayyari.UserService.Entity.QuizAttempt;
import com.tayyari.UserService.Entity.UserEnrollment;
import com.tayyari.UserService.Entity.UserProgress;
import com.tayyari.UserService.Exception.BusinessException;
import com.tayyari.UserService.Repo.QuizAttemptRepo;
import com.tayyari.UserService.Repo.UserEnrollmentRepo;
import com.tayyari.UserService.Repo.UserProgressRepo;
import com.tayyari.UserService.Service.Interfaces.ProgressTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.tayyari.UserService.ENUMS.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {
    private static final Logger logger = LoggerFactory.getLogger(ProgressTrackingServiceImpl.class);
    @Autowired
    private QuizAttemptRepo attemptRepository;
    @Autowired
    private UserProgressRepo progressRepository;
    @Autowired
    private UserEnrollmentRepo enrollmentRepository;

    @Override
    public RecordAttemptResponseDTO recordQuizAttempt(AttemptRequestDto request) {
        logger.info("Entering into  recordQuizAttempt method with RequestDto: {} ",request);
        // Get enrollment
        UserEnrollment enrollment = enrollmentRepository.findByUserIdAndQuizId(request.getUserId(), request.getQuizId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NO_AVAILABLE_ENROLLMENT));
        logger.info("UserEnrollment : {} ",enrollment);
        // Get next attempt number
        Integer nextAttemptNumber = attemptRepository.findMaxAttemptNumber(request.getUserId(), request.getQuizId())
                .orElse(0) + 1;
        logger.info("Attempt Number : {} ",nextAttemptNumber);
        // Create attempt
        try {
            QuizAttempt attempt = new QuizAttempt();
            attempt.setQuizId(request.getQuizId());
            attempt.setUserId(request.getUserId());
            attempt.setEnrollment(enrollment);
            attempt.setAttemptNumber(nextAttemptNumber);
            attempt.setStartedAt(request.getStartedAt());
            attempt.setCompletedAt(request.getCompletedAt());
            attempt.setAttemptStatus(request.getStatus());
            attempt.setScore(request.getScore().doubleValue());
            attempt.setMaxScore(request.getMaxScore().doubleValue());
            attempt.setPercentage(request.getPercentage());
            attempt.setTimeTaken(request.getTimeTaken());
            attempt.setCorrectAnswers(request.getCorrectAnswers());
            attempt.setIncorrectAnswers(request.getIncorrectAnswers());
            attempt.setUniqueKey(request.getUniqueKey());
            attempt.setUnanswered(request.getUnanswered());
            attempt.setTotalQuestions(request.getTotalQuestions());
            attempt.setAnswers(request.getAnswerDetails());

            QuizAttempt sa = attemptRepository.save(attempt);
            logger.info("QuizAttempt : {} ",sa);

            RecordAttemptResponseDTO ard=new RecordAttemptResponseDTO();
            ard.setUniqueKey(sa.getUniqueKey());
            ard.setScore(sa.getScore());
            ard.setMaxScore(sa.getMaxScore());
            ard.setTimeTaken(sa.getTimeTaken());
            ard.setCorrectAnswers(sa.getCorrectAnswers());
            ard.setIncorrectAnswers(sa.getIncorrectAnswers());
            ard.setUnanswered(sa.getUnanswered());
            return ard;



        } catch (Exception e) {
            logger.info("Inside catch block  : {}",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProgress> getUserProgress(Long userId) {
        logger.info("method getUserProgress : {} ",userId);
        List<UserProgress> progressList = progressRepository.findByUserId(userId);
        logger.info("List of getUserProgress : {} ",userId);
        return progressList;
    }

    @Override
    public ProgressResponseDto getQuizProgress(Long userId, Long quizId) {
        return null;
    }

    @Override
    public List<AttemptResponseDto> getUserAttempts(Long userId, Long quizId) {

        List<QuizAttempt> attempts = attemptRepository.findByUserIdAndQuizId(userId, quizId);
        List<AttemptResponseDto> listAR= new ArrayList<>();
        for(QuizAttempt attempt:attempts)
        {
            AttemptResponseDto ard = new AttemptResponseDto();
            ard.setUserId(attempt.getUserId());
            ard.setQuizId(attempt.getQuizId());
            ard.setEnrollmentId(attempt.getEnrollment().getUserEnrollmentId());
            ard.setAttemptNumber(attempt.getAttemptNumber());
            ard.setStartedAt(attempt.getStartedAt());
            ard.setCompletedAt(attempt.getCompletedAt());
            ard.setStatus(attempt.getAttemptStatus().name());   // example status
            ard.setScore(attempt.getScore());
            ard.setMaxScore(attempt.getMaxScore());
            ard.setPercentage(attempt.getPercentage());
            ard.setTimeTaken(attempt.getTimeTaken());
            ard.setCorrectAnswers(attempt.getCorrectAnswers());
            ard.setIncorrectAnswers(attempt.getIncorrectAnswers());
            ard.setUnanswered(attempt.getUnanswered());
            ard.setTotalQuestions(attempt.getTotalQuestions());
            ard.setAnswerDetails(attempt.getAnswers() != null ? attempt.getAnswers() : new ArrayList<>());
            listAR.add(ard);


        }
        logger.info("AttemptResponseDto : {} ",listAR);

        return listAR;
    }
}

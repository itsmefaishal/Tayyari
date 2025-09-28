package com.tayyari.UserService.Service.Interfaces;

import com.tayyari.UserService.DTO.EnrollmentRequestDto;
import com.tayyari.UserService.DTO.EnrollmentResponseDto;
import com.tayyari.UserService.Entity.UserEnrollment;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponseDto enrollUserInQuiz(EnrollmentRequestDto request);

    List<EnrollmentResponseDto> getUserEnrollments(Long userId, UserEnrollment.EnrollmentStatus status);

   // EnrollmentResponseDto updateEnrollment(Long userId, Long enrollmentId, UpdateEnrollmentRequestDto request);

    void unenrollFromQuiz(Long userId, Long enrollmentId);

    EnrollmentResponseDto getEnrollment(Long userId, Long enrollmentId);

    boolean isUserEnrolledInQuiz(Long userId, Long quizId);

    List<EnrollmentResponseDto> getEnrollmentsByCategory(String category);
}

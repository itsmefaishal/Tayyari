package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEnrollmentRepo extends JpaRepository<UserEnrollment,Long> {
    List<UserEnrollment> findByUserIdAndStatus(Long userId, UserEnrollment.EnrollmentStatus status);

    List<UserEnrollment> findByUserId(Long userId);

    UserEnrollment findByUserIdAndUserEnrollmentId(Long userId, Long enrollmentId);



    Optional<Object> findByUserIdAndQuizId(Long userId, Long quizId);

    List<UserEnrollment> findByExamCategory(String category);
}

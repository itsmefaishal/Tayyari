package com.tayyari.UserService.Service.Interfaces;

import com.tayyari.UserService.DTO.AttemptRequestDto;
import com.tayyari.UserService.DTO.AttemptResponseDto;
import com.tayyari.UserService.DTO.ProgressResponseDto;
import com.tayyari.UserService.Entity.UserProgress;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProgressTrackingService {

    public AttemptResponseDto recordQuizAttempt( AttemptRequestDto request);
    public List<UserProgress> getUserProgress(Long userId);
    public ProgressResponseDto getQuizProgress(Long userId, Long quizId);
    public List<AttemptResponseDto> getUserAttempts(Long userId, Long quizId, Pageable pageable);
}

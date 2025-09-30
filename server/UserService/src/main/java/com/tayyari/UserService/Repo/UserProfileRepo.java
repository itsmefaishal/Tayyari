package com.tayyari.UserService.Repo;

import com.tayyari.UserService.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepo extends JpaRepository<UserProfile,Long> {
    UserProfile findByUserProfileId(Long userId);
}

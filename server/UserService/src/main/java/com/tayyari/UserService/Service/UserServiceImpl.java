package com.tayyari.UserService.Service;

import com.tayyari.UserService.DTO.UserResponseFromAuthSerDTO;
import com.tayyari.UserService.Entity.UserProfile;
import com.tayyari.UserService.Repo.UserProfileRepo;
import com.tayyari.UserService.Service.Interfaces.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Override
    public UserProfile getUser(Long userId) {
        return   userProfileRepo.findByUserProfileId(userId);


    }

    @Override
    public UserProfile addUser(UserResponseFromAuthSerDTO userResponse) {
        UserProfile up = new UserProfile();
        up.setAuthUserId(userResponse.getAuthUserId());
        up.setFullName(userResponse.getFullName());
        up.setUserStatus(userResponse.getUserStatus());
        up.setCreatedAt(userResponse.getCreatedAt());
        up.setEmail(userResponse.getEmail());
        userProfileRepo.save(up);

        return up;
    }

}

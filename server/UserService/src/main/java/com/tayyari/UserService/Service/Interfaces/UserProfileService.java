package com.tayyari.UserService.Service.Interfaces;

import com.tayyari.UserService.DTO.UserResponseFromAuthSerDTO;
import com.tayyari.UserService.Entity.UserProfile;

public interface UserProfileService {

    public UserProfile getUser(Long userId);
    public UserProfile addUser(UserResponseFromAuthSerDTO userProfile);

}

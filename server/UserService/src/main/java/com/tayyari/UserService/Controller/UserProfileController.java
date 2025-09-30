package com.tayyari.UserService.Controller;

import com.tayyari.UserService.DTO.ApiResponse;
import com.tayyari.UserService.DTO.UserResponseFromAuthSerDTO;
import com.tayyari.UserService.Entity.UserProfile;
import com.tayyari.UserService.Service.Interfaces.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user-service")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfile;

    @PostMapping("/post-user-to-userService")
    public ApiResponse<UserProfile> getUserDataFromAuthSer(@RequestBody UserResponseFromAuthSerDTO ur)
    {
        try {
            UserProfile savedProfile = userProfile.addUser(ur);
            return new ApiResponse<>(true,"User posted successfully in UserService",savedProfile);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false,"User posting failed in UserService",null);

        }


    }


}

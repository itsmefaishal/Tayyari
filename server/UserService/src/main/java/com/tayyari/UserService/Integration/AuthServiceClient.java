package com.tayyari.UserService.Integration;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="user-Service", path = "/user")
public interface AuthServiceClient {
    @GetMapping("/getUser")
    Boolean isUserActive(Long UserId);
}

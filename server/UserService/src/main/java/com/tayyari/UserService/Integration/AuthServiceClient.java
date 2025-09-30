package com.tayyari.UserService.Integration;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="AuthService", path = "/auth")
public interface AuthServiceClient {
    @GetMapping("/user-status")
    Boolean isUserActive(Long UserId);
}

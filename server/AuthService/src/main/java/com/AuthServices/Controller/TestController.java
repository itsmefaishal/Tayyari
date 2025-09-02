package com.AuthServices.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/testAuth")
    public String testAuth()
    {
        return   "JWT wala authentication ho raha hi!!!!!!!!!!!!!";
    }
}

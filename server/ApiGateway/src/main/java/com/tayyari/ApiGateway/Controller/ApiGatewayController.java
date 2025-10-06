package com.tayyari.ApiGateway.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiGatewayController {
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("API Gateway UPPPPPPP!!!!");
    }
}

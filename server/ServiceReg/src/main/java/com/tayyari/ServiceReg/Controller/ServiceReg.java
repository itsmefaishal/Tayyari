package com.tayyari.ServiceReg.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServiceReg {
    
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Service registry UPPPPPPP!!!!");
    }
}

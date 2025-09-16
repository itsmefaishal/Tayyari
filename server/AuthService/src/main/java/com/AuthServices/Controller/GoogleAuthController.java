package com.AuthServices.Controller;

import com.AuthServices.Entity.Role;
import com.AuthServices.Entity.User;
import com.AuthServices.Repository.RoleRepo;
import com.AuthServices.Repository.UserRepo;
import com.AuthServices.Service.JwtUtil;
import com.AuthServices.Service.MyUserDetailsService;
import com.AuthServices.Service.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/auth/google")
@CrossOrigin(origins = "http://localhost:3000") // Add CORS support
public class GoogleAuthController {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepository;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "http://localhost:9090/auth/google/callback");
            params.add("grant_type", "authorization_code");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");
                String firstName = (String) userInfo.get("given_name");
                String lastName = (String) userInfo.get("family_name");
                UserDetails userDetails = null;
                try {
                    userDetails = (UserDetails) userDetailsService.loadUserByUsername(email);
                } catch (Exception e) {
                    User user = new User();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setCreationDate(LocalDate.now());
                    user.setPassword(passwordEncoder.encode(email));
                    user.setStatus("ACTIVE");
                    Set<Role> roles = new HashSet<>();
                    Role defaultRole = roleRepo.findByRoleName("USER")
                            .orElseThrow(() -> new RuntimeException("Default role USER not found in DB"));
                    roles.add(defaultRole);
                    user.setRoles(roles);
                    userRepository.save(user);
                }

                userDetails = (UserDetails) userDetailsService.loadUserByUsername(email);
                String jwtToken = jwtUtil.generateToken(userDetails);

//                // Option 1: Return JSON response with token
//                Map<String, Object> response = new HashMap<>();
//                response.put("token", jwtToken);
//                response.put("email", email);
//                response.put("firstName", firstName);
//                response.put("lastName", lastName);
//                response.put("message", "Login successful");
//
//                return ResponseEntity.ok(response);

                // Option 2: If you prefer redirect, uncomment below and comment above
                
                String redirectUrl = "http://localhost:3000/dashboard?token=" + jwtToken;
                return ResponseEntity.status(HttpStatus.FOUND)
                                     .header(HttpHeaders.LOCATION, redirectUrl)
                                     .build();

            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            System.out.println("Exception occurred while handleGoogleCallback " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
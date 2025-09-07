package com.AuthServices.Controller;

import com.AuthServices.CustomException.RoleNotFoundException;
import com.AuthServices.DTO.*;
import com.AuthServices.Entity.Role;
import com.AuthServices.Entity.User;
import com.AuthServices.Repository.RoleRepo;
import com.AuthServices.Repository.UserRepo;
import com.AuthServices.Response.ApiResponse;
import com.AuthServices.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody UserDTO user)
    {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent())
        {
            return  ResponseEntity.ok(new ApiResponse<>(false,"User Already Registered",byEmail.get()));
        }

        System.out.println("inside adduser mapping"+user.toString());

        try
        {
            String firstName = null;
            String lastName = null;
            String userName = null;
            String password = null;
            String status = "INACTIVE";
            Set<Role> roles = new HashSet<>();

            //First Name
            if (user.getFirstName() != null && !user.getFirstName().isBlank()) {
                firstName = user.getFirstName().toUpperCase().trim();
            } else return ResponseEntity.ok(new ApiResponse<>(false,"First Name cannot be blank !!",null));

            //Last Name
            if (user.getLastName() != null && !user.getLastName().isBlank()) {
                lastName = user.getLastName().toUpperCase().trim();
            } else return ResponseEntity.ok(new ApiResponse<>(false,"Last Name cannot be blank !!",null));

            //Username
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                userName = user.getEmail();
            } else return ResponseEntity.ok(new ApiResponse<>(false,"Email cannot be blank !!",null));

            // Password (encode, and if empty ,fallback to username)
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                password = encoder.encode(user.getPassword().trim());
            } else {
                password = encoder.encode(userName); // default fallback
            }

            //Roles (from DB, default = USER)
            if (user.getRole() == null || user.getRole().isEmpty()) {
                Role defaultRole = roleRepo.findByRoleName("USER")
                        .orElseThrow(() -> new RoleNotFoundException("Role does not exists"));
                roles.add(defaultRole);
            } else {
                for (String roleName : user.getRole()) {
                    Role r = roleRepo.findByRoleName(roleName)
                            .orElseThrow(() -> new RoleNotFoundException("Role does not exists" + roleName));
                    roles.add(r);
                }
            }

            // Status (default = InActive)
            if (user.getStatus() != null || !user.getStatus().isBlank()) {
                status = "INACTIVE";
            }

            //Finally call service
            User userCreated = userService.addUser(firstName, lastName, userName, password, status, roles);
                return ResponseEntity.ok(new ApiResponse<>(true,"User Created Succesfully",userCreated));

        }
        catch(RoleNotFoundException e)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Role not present in DB", null));
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Kuch to Gadbad hai Daya", null));

        }


    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@Valid @RequestBody VerifyOTPRequest request) {
        try {
            String message = userService.verifyUserEmail(request.getEmail(), request.getOtpCode());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", message
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOTP(@Valid @RequestBody ResendOTPRequest request) {
        try {
            String message = userService.resendOTP(request.getEmail());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", message
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/forgot-Password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam  String email)
    {
        Integer s = userService.passReset(email);
        if(s==3)
        { return ResponseEntity.ok(new ApiResponse<>(true,"OTP sent Successfully : "+ email,null));}
        if(s==-1)
        { return ResponseEntity.ok(new ApiResponse<>(false,"User Not found",null));}
        if(s==1)
        { return ResponseEntity.ok(new ApiResponse<>(false,"Email does not match with user :" + email,null));}
        else
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Kuch to Gadbad hai Daya", null));

    }

    @PostMapping("/verify-otp-reset-password")
    public  ResponseEntity<ApiResponse<String>> verifyOtpEmailResetPassword(@RequestBody ResetPasswordDTO rpd)
    {
        if( !rpd.getEmail().isBlank() || !rpd.getPassword().isBlank() || !rpd.getOtpCode().isBlank())
        {
            Integer retCode = userService.verifyEmailAndOtpAndPassReset(rpd.getEmail(), rpd.getPassword(), rpd.getOtpCode());
            if(retCode==2)
            {
                return ResponseEntity.ok(new ApiResponse<>(true,"Password Changed Successfully" ,null));
            }
            if(retCode==3)
            {
                return ResponseEntity.ok(new ApiResponse<>(true,"Invalid OTP, Please try again" ,null));
            }
            else
                ResponseEntity.ok(new ApiResponse<>(false,"kuch to gadbad hai DAYA" ,null));
        }
        return ResponseEntity.ok(new ApiResponse<>(false," Fields cannot be Blank" ,null));
    }

    @PostMapping("/addRole")
    public Role addRole(@RequestBody RoleDTO role)
    {
        System.out.println("inside add role called:::::::");
        Role roleNew= new Role(role.getRoleName());
        return   roleRepo.save(roleNew);
    }
    @GetMapping("/getRole")
    public List<Role> getRole()
    {
        return   roleRepo.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUser")
    public List<User> getUser()
    {
        return   userRepository.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/testAuth")
    public String testAuth()
    {
        return   "JWT wala authentication ho raha hi!!!!!!!!!!!!!";
    }
}


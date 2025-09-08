package com.AuthServices.Service;

import com.AuthServices.Entity.Role;
import com.AuthServices.Entity.User;
import com.AuthServices.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    OTPService otpService;
    @Autowired
    PasswordEncoder encoder;

    public User addUser(String fname, String lname, String uName, String password,String status, Set<Role> role )
    {
       try {
           User user = new User(fname,lname,uName,password,status,role);
           User save = userRepo.save(user);

           return save;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    public String verifyUserEmail(String email, String otpCode) {
        // Find user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify OTP
        if (!otpService.verifyOTP(email, otpCode)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Activate user
        user.setStatus("ACTIVE");
        userRepo.save(user);

        return "Email verified successfully. User is now active.";
    }

    public String resendOTP(String email) {
       /*  Check if user exists
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Don't resend if user is already active
        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException("User is already verified");
        }*/

        // Generate and send new OTP
        try {
            return otpService.generateAndSendOTP(email);
        } catch (Exception e) {
            throw new RuntimeException("Error sending OTP");
        }
    }


    public Integer passReset(String email) {
        try
        {
            Optional<User> byEmail = userRepo.findByEmail(email);
            if(byEmail.isEmpty())
            {
                return -1; //User not Found
            }
            if(!byEmail.get().getEmail().equalsIgnoreCase(email))
            {
                return 1; //Email does not match with User
            }
            String s = otpService.generateAndSendOTP(email);
            if(s!=null) return 3; // for successfully sending otp
            else return 2; // returned server error

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer verifyEmailAndOtpAndPassReset(String email,String pass,String otp)
    {
        try
        {
            boolean b = otpService.verifyOTP(email, otp);
            if(b)
            {
                Optional<User> byEmail = userRepo.findByEmail(email);
                if(byEmail.isEmpty())
                    return -1; //User not found
                User userDb = byEmail.get();
                userDb.setPassword(encoder.encode(pass));
                    userRepo.save(userDb);
                    return 2; //Password Changed Successfully

            }
            else return 3; //Invalid OTP, Please try again
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

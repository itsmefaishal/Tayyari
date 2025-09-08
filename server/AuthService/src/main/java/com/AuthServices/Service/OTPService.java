package com.AuthServices.Service;

import com.AuthServices.Entity.OTP;
import com.AuthServices.Repository.OTPRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class OTPService {

    @Autowired
    private OTPRepo otpRepository;

    @Autowired
    private EmailService emailService;

    private static final int OTP_EXPIRY_MINUTES = 5;

    public String generateAndSendOTP(String email) {
        // Clear any existing unused OTPs for this email
        clearExistingOTPs(email);

        // Generate 6-digit OTP
        String otpCode = generateOTP();

        // Save OTP to database
        OTP otp = new OTP(email, otpCode, OTP_EXPIRY_MINUTES);
        otpRepository.save(otp);

        // Send email
        try {
            emailService.sendOTPEmail(email, otpCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP ");
        }

        return "OTP sent successfully to " + email;
    }

    public boolean verifyOTP(String email, String otpCode) {
        Optional<OTP> otpOptional = otpRepository.findByEmailAndOtpCodeAndUsedFalse(email, otpCode);

        if (otpOptional.isEmpty()) {
            return false;        }

        OTP otp = otpOptional.get();
        if (otp.isExpired()) {
            return false;
        }
        // Mark OTP as used
        otp.setUsed(true);
        otpRepository.save(otp);

        return true;
    }

    //Generating Otp
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit number
        return String.valueOf(otp);
    }

    private void clearExistingOTPs(String email) {
        otpRepository.deleteByEmail(email);
    }

    public boolean hasExpiredOTP(String email) {
        List<OTP> otps = otpRepository.findByEmailAndUsedFalse(email);
        return otps.stream().anyMatch(OTP::isExpired);
    }
}

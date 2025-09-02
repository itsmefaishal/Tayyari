package com.AuthServices.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOTPEmail(String toEmail, String otpCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Email Verification - OTP Code");
            message.setText(buildOTPEmailBody(otpCode));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String buildOTPEmailBody(String otpCode) {
        return "Dear User,\n\n" +
                "Your email verification code is: " + otpCode + "\n\n" +
                "This code will expire in 5 minutes.\n" +
                "Please do not share this code with anyone.\n\n" +
                "Best regards,\n" +
                "Team Tayyari";
    }
}

package com.AuthServices.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailServiceBrevo emailServiceBrevo;

    //@Value("${spring.mail.username}")
    private String fromEmail ="pata51522@gmail.com";

    public void sendOTPEmail(String toEmail, String otpCode) {
        /*try {
            System.out.println("inside try block of sendOtpEmail ");
            SimpleMailMessage message = new SimpleMailMessage();
            System.out.println("fromEmail :" + fromEmail);
            message.setFrom(fromEmail);
            System.out.println("toEmaill " +toEmail);
            message.setTo(toEmail);
            message.setSubject("Email Verification - OTP Code");
            message.setText(buildOTPEmailBody(otpCode));

            try {
                mailSender.send(message);
            } catch (MailException e) {
                e.printStackTrace();
                System.out.println("inside catch block of mail sender");
                throw new RuntimeException(e);

            }
        } catch (Exception e) {
			System.out.println("inside catch block of sendOtpEmail ");
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }*/
        try{
            emailServiceBrevo.sendEmail(toEmail,"Email Verification - OTP Code",buildOTPEmailBody(otpCode));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

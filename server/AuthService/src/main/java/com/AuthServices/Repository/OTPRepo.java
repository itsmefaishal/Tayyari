package com.AuthServices.Repository;

import com.AuthServices.Entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmailAndOtpCodeAndUsedFalse(String email, String otpCode);
    List<OTP> findByEmailAndUsedFalse(String email);
    void deleteByEmail(String email);
}

package com.AuthServices.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ResetPasswordDTO {
    @Email
    @JsonProperty("email")
    private String email;
    @NotNull
    @JsonProperty("password")
    @Size(min = 6)
    private String password;
    @JsonProperty("otpCode")
    @NotBlank
    @Size(min = 6, max = 6)
    private String otpCode;

    public ResetPasswordDTO() {
    }

    public ResetPasswordDTO(String email, String password, String otpCode) {
        this.email = email;
        this.password = password;
        this.otpCode = otpCode;
    }


    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

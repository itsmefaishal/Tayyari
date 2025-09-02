package com.AuthServices.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("password")
    @NotBlank
    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }
}

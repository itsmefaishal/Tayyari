package com.tayyari.UserService.DTO;

import jakarta.persistence.Column;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class UserResponseFromAuthSerDTO {
    private static final Logger log = LogManager.getLogger(UserResponseFromAuthSerDTO.class);
    private Long authUserId;
    private String fullName;
    private String userStatus;
    private String email;
    private LocalDate createdAt;

    public UserResponseFromAuthSerDTO() {
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserResponseFromAuthSerDTO{" +
                "authUserId=" + authUserId +
                ", fullName='" + fullName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

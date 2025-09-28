package com.tayyari.UserService.Entity;

import jakarta.persistence.*;


@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileId;
    @Column
    private String fullName;
    @Column
    private String education;
    @Column
    private String casteCategory;
    @Column
    private String ProfilePictureURL;
    @Column
    private String updatedAt;


    public UserProfile() {
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCasteCategory() {
        return casteCategory;
    }

    public void setCasteCategory(String casteCategory) {
        this.casteCategory = casteCategory;
    }

    public String getProfilePictureURL() {
        return ProfilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        ProfilePictureURL = profilePictureURL;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userProfileId=" + userProfileId +
                ", fullName='" + fullName + '\'' +
                ", education='" + education + '\'' +
                ", casteCategory='" + casteCategory + '\'' +
                ", ProfilePictureURL='" + ProfilePictureURL + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}

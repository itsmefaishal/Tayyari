package com.AuthServices.DTO;

import com.AuthServices.Entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class UserDTO {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String  lastName;

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private Set<String> role;
    private String status ="INACTIVE";

    public UserDTO() {
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO(String firstName,String lastName,String email, String password, Set<String> role, String status) {
       this.firstName=firstName;
       this.lastName=lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void setRolename(Set<String> rolename) {
        this.role = rolename;
    }

    public String getStatus() {
        return status;
    }

    public void setEnabled(String enabled) {
        status = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}

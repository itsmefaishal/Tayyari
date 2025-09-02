package com.AuthServices.DTO;

public class RoleDTO {
    private String role;

    public RoleDTO()
    {

    }

    public RoleDTO(String role) {
        this.role = role;
    }
    public String getRoleName() {
        return role;
    }



    public void setRole(String role) {
        this.role = role;
    }
}

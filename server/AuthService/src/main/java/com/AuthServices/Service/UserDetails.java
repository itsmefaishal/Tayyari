package com.AuthServices.Service;

import com.AuthServices.Entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{

    private final User user;
    public UserDetails(User user)
    {
        this.user=user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(" inside UserDetails : granted authorities");
        return user.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getroleName())
                .toList();
    }



    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        System.out.println(" inside UserDetails : isEnabled check" +user.getStatus() );
        return "ACTIVE".equalsIgnoreCase(user.getStatus());


    }
}

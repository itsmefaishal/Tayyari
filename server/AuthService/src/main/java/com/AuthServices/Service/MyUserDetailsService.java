package com.AuthServices.Service;

import com.AuthServices.Entity.User;
import com.AuthServices.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService  implements UserDetailsService {
    @Autowired
    UserRepo uRepo;

    public User findByUsername(String user)
    {
        Optional<User> byUserName = uRepo.findByEmail(user);
        User user1=byUserName.get();
        return user1;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("inside loadUserbyUsername : UserDetailsService ");
        User user = uRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not found"));
        System.out.println("after loadUserByUserName" + user.toString());


        System.out.println(user.toString());
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getroleName()))
                .collect(Collectors.toSet());
        System.out.println("Authorities: " + authorities);

        return new  com.AuthServices.Service.UserDetails(user);

    }
}

package com.AuthServices.Controller;

import com.AuthServices.DTO.LoginDTO;
import com.AuthServices.Entity.User;
import com.AuthServices.Repository.UserRepo;
import com.AuthServices.Service.JwtUtil;
import com.AuthServices.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/test")
    public ResponseEntity<String> testPoint(){
        return ResponseEntity.ok("Auth Service Working !!!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginControl(@RequestBody LoginDTO loginDto)
    {
        if(loginDto.getEmail().isBlank() || loginDto.getPassword().isBlank())
        {
            return new ResponseEntity<>("UserName or Password cannot be blank" , HttpStatus.BAD_REQUEST);
        }
        System.out.println(loginDto.getEmail() + ":::" + loginDto.getPassword());
        try {

            Authentication auth=authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginDto.getEmail().trim(),loginDto.getPassword().trim()));
            System.out.println("after authentication is successful: ");
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Optional<User> user = userRepo.findByEmail(userDetails.getUsername());


                   String token = jwtUtil.generateToken(userDetails);
//            System.out.println(token);
                Map<String,String> response= new HashMap<>();
                response.put("name",user.get().getFirstName()+" "+user.get().getLastName());
                response.put("jwt",token);
                return new ResponseEntity<>(response, HttpStatus.OK);

           }
           catch(DisabledException e )
           {
               return new ResponseEntity<>("User InActive",HttpStatus.FORBIDDEN);
           }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid User Name or Password ",HttpStatus.BAD_GATEWAY);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Login Failed, Kuch to gadbad hai Daya",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // @GetMapping("/test")
    // public String authTest()
    // {
    //     return "Authcontroller zinda hai";
    // }

    @GetMapping("/user-status")
	public Boolean UserStatus()
    {
		System.out.println("inside user status ");
        return true;
    }

}


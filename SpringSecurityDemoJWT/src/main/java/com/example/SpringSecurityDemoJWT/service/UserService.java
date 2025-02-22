package com.example.SpringSecurityDemoJWT.service;

import com.example.SpringSecurityDemoJWT.model.UserModel;
import com.example.SpringSecurityDemoJWT.repo.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@NoArgsConstructor
public class UserService {

    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepo userRepo,AuthenticationManager authenticationManager ,JwtService jwtService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

//    register user to the db
    public UserModel registerUser(UserModel userModel) {

        if(userModel != null) {
            UserModel user = userRepo.findByUsername(userModel.getUsername());

            if(user == null){
                userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
                return userRepo.save(userModel);
            }
        }
        return null;
    }

//    login
    public String logInUser(UserModel userModel) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userModel.getUsername(), userModel.getPassword()));

            if(authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authentication.getName());
                return ("Logged in "+authentication.getName()+" "+token);
            }else {
                return "Login Failed";
            }
        }catch(Exception e){
            return e.getMessage();
        }
    }
}

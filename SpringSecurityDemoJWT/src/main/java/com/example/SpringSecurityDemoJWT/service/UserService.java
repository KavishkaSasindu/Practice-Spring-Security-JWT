package com.example.SpringSecurityDemoJWT.service;

import com.example.SpringSecurityDemoJWT.model.UserModel;
import com.example.SpringSecurityDemoJWT.repo.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@NoArgsConstructor
public class UserService {

    private UserRepo userRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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



}

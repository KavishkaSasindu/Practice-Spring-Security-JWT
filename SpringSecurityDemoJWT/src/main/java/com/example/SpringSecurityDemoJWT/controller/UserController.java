package com.example.SpringSecurityDemoJWT.controller;

import com.example.SpringSecurityDemoJWT.model.UserModel;
import com.example.SpringSecurityDemoJWT.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@NoArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    register request
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserModel userModel) {
        if(userModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserModel is null");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userModel));
    }

//    login
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody UserModel userModel) {
        try{
            if(userModel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is null");
            }

            return ResponseEntity.status(HttpStatus.OK).body(userService.logInUser(userModel));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

package com.taskmanagerrest.taskmanager.controller;

import javax.security.auth.login.CredentialException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;
import com.taskmanagerrest.taskmanager.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class AuthController {
    
    private final IUserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody User user) throws CredentialException, UserNotFoundException, UserNotEnabledException{
        User userLogin = userService.checkCredentiales(user);

        if(userLogin!= null){
            String tokeJWT = jwtUtil.create(String.valueOf(userLogin.getId()),userLogin.getEmail());
            return ResponseEntity.ok(tokeJWT);
        }

        return new ResponseEntity<>("Invalid credentials",HttpStatus.BAD_REQUEST);
    }

}

package com.taskmanagerrest.taskmanager.controller;

import javax.security.auth.login.CredentialException;

import com.taskmanagerrest.taskmanager.security.entities.JwtDto;
import com.taskmanagerrest.taskmanager.security.entities.LoginUser;
import com.taskmanagerrest.taskmanager.security.entities.Message;
import com.taskmanagerrest.taskmanager.security.util.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;

import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AuthController {
    private  final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final IUserService userService;
    private  final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUser loginUser, BindingResult bindingResult) throws CredentialException, UserNotFoundException, UserNotEnabledException {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Revise sus credenciales"), HttpStatus.BAD_REQUEST);
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("autentication");
            System.out.println(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.taskmanagerrest.taskmanager.controller;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;

import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin
public class AuthController {
    private  final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final IUserService userService;
    private  final JwtProvider jwtProvider;
    private  final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) throws CredentialException, UserNotFoundException, UserNotEnabledException {
        User userLogin = userService.findByEmail(loginUser.getEmail());
        if(userLogin == null){
            return new ResponseEntity<>(new Message("Usuario no existe"),HttpStatus.BAD_REQUEST);
        } else if(!userLogin.isEnabled()){
            return new ResponseEntity<>(new Message("Usuario deshabilitado"),HttpStatus.UNAUTHORIZED);
        }
        
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Message("Revisa tus credenciales"), HttpStatus.BAD_REQUEST);
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

            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);       }
       
    }
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody  UserDto user){
        System.out.println(user.toString());
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                
            }
        
            
    }
      
    
}

package com.taskmanagerrest.taskmanager.controller;

import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController @RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class UserController     {


    
    private final   IUserService userService;

    private  final PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public ResponseEntity<List<User>> findAll(){

        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto user) throws UserAlreadyExistsException{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException{
        userService.deleteUser(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

   

}

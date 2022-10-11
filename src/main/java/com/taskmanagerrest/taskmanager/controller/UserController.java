package com.taskmanagerrest.taskmanager.controller;

import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;
import com.taskmanagerrest.taskmanager.util.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class UserController     {

    private final JWTUtil jwtUtil;
    
    private final   IUserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<User>> findAll(@RequestHeader(value = "Authorization") String token){
        if(!jwtUtil.validartoken(token)){
            return null;
        }
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto user) throws UserAlreadyExistsException{
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException{
        userService.deleteUser(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

   

}

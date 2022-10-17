package com.taskmanagerrest.taskmanager.controller;


import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.dto.UserEditDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;



@RestController @RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin
public class UserController     {

    
    private final   IUserService userService;    
    private  final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){

        return ResponseEntity.ok(userService.findAll());
    }


    @DeleteMapping("user/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException{
        userService.deleteUser(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<Void> updateUser(@Valid @PathVariable Long id,@RequestBody UserEditDto user) throws UserNotFoundException{
        User UserToUpdate = userService.finById(id);
        if(UserToUpdate == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }       
        UserToUpdate.setEmail(user.getEmail());
        UserToUpdate.setName(user.getName());
        UserToUpdate.setLastName(user.getLastName());
        UserToUpdate.setImage(user.getImage());
       
        userService.updateUSer(UserToUpdate);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    
    @PutMapping("status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) throws UserNotFoundException{
        User UserToUpdate = userService.finById(id);
        if(UserToUpdate == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean status = UserToUpdate.isEnabled();
        UserToUpdate.setEnabled(!status);
       
        userService.updateUSer(UserToUpdate);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException{
        return ResponseEntity.ok(userService.finById(id));
    }

   

}

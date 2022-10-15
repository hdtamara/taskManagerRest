package com.taskmanagerrest.taskmanager.controller;


import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController @RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController     {

    
    private final   IUserService userService;    

    @GetMapping
    public ResponseEntity<List<User>> findAll(){

        return ResponseEntity.ok(userService.findAll());
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException{
        userService.deleteUser(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

   

}

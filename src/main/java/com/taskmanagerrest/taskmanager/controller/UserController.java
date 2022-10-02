package com.taskmanagerrest.taskmanager.controller;

import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController     {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/user/{id}")
    public  void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

    }

}

package com.taskmanagerrest.taskmanager.controller;

import com.taskmanagerrest.taskmanager.dto.TaskDto;
import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.TaskNotFoundException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.services.ITaskService;
import com.taskmanagerrest.taskmanager.services.IUserService;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController @RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {


    
    private final ITaskService taskService;
   
    private  final IUserService userService;

    @GetMapping("/task")
    public ResponseEntity<List<Task>> findAll(@RequestHeader(value = "Authorization") String token)  {
        System.out.println(token);        


        
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping("/admin")
    public ResponseEntity<Task> createTask(@RequestBody @Validated TaskDto task, @RequestHeader(value = "Authorization") String token) throws UserNotFoundException{

        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestHeader(value = "Authorization") String token) throws TaskNotFoundException{

        return new ResponseEntity<>(taskService.updatTask(id),HttpStatus.ACCEPTED) ;
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,@RequestHeader(value = "Authorization") String token) throws TaskNotFoundException{

        taskService.deleteTask(id);
         return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/task/{idUser}")
    public ResponseEntity<List<Task>> findByUser(@PathVariable Long idUser,@RequestHeader(value = "Authorization") String token) throws UserNotFoundException{

        User user = userService.finById(idUser);
        return ResponseEntity.ok(taskService.finByUser(user));
    };
}

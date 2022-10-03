package com.taskmanagerrest.taskmanager.controller;

import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.repository.TaskRepositoy;
import com.taskmanagerrest.taskmanager.repository.UserRepository;
import com.taskmanagerrest.taskmanager.services.ITaskService;
import com.taskmanagerrest.taskmanager.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private  IUserService userService;

    @GetMapping("/task")
    public List<Task> findAll(){
        return taskService.findAll();
    }

    @PostMapping("/task")
    public Task createTask(@RequestBody Task task){
        System.out.println(task);
        return  taskService.createTask(task);
    }

    @PutMapping("/task/{id}")
    public boolean updateTask(@PathVariable Long id){
        return taskService.updatTask(id);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @GetMapping("/task/{idUser}")
    public List<Task> findByUser(@PathVariable Long idUser){
        User user = userService.finById(idUser);
        return taskService.finByUser(user);
    };
}

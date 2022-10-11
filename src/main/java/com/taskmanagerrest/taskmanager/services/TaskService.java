package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.dto.TaskDto;
import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.TaskNotFoundException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.repository.TaskRepositoy;
import com.taskmanagerrest.taskmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements  ITaskService{
    @Autowired
    TaskRepositoy taskRepositoy;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Task> findAll() {
        return (List<Task>) taskRepositoy.findAll();
    }

    @Override
    public Task createTask(TaskDto taskDto) throws UserNotFoundException{
        Optional<User> user = userRepository.findById(taskDto.getUserId());
        if(user.isEmpty())
            throw new UserNotFoundException("user not found with id : "+ taskDto.getUserId());
        Task task = Task.build(0, taskDto.getDescription(), false, taskDto.getFinishDate(), user.get());
        return taskRepositoy.save(task);
    }

    @Override
    public Task updatTask(Long id) throws TaskNotFoundException {
        Optional<Task> task = taskRepositoy.findById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("task not found with id : "+id);
        }
        task.get().setDone(true);
        return taskRepositoy.save(task.get());            
        
    }

    @Override
    public void deleteTask(Long id) throws TaskNotFoundException {
        Optional<Task> task = taskRepositoy.findById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("task not found with id : "+id);
        }
        taskRepositoy.deleteById(id);
    }

    @Override
    public List<Task> finByUser(User user) throws UserNotFoundException {
        Optional<User> userTask = userRepository.findById(user.getId());
        if(userTask.isEmpty())
            throw new UserNotFoundException("user not found with id : "+user.getId());
        return  taskRepositoy.findByUser(user);
    }
}

package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.dto.TaskDto;
import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.TaskNotFoundException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;

import java.util.List;

public interface ITaskService {

    List<Task> findAll();

    public Task createTask(TaskDto task) throws UserNotFoundException;

    public Task updatTask(Long id) throws TaskNotFoundException;

    public void deleteTask(Long id) throws TaskNotFoundException;

    public List<Task> finByUser(User user) throws UserNotFoundException;
}

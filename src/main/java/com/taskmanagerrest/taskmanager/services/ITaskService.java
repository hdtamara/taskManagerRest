package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;

import java.util.List;

public interface ITaskService {

    List<Task> findAll();

    public Task createTask(Task task);

    public boolean updatTask(Long id);

    public void deleteTask(Long id);

    public List<Task> finByUser(User user);
}

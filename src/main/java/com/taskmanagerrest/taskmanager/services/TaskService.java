package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.repository.TaskRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements  ITaskService{
    @Autowired
    TaskRepositoy taskRepositoy;

    @Override
    public List<Task> findAll() {
        return (List<Task>) taskRepositoy.findAll();
    }

    @Override
    public Task createTask(Task task) {
        Task task1 = taskRepositoy.save(task);
        return task1;
    }

    @Override
    public boolean updatTask(Long id) {
        Optional<Task> task = taskRepositoy.findById(id);
        if(task.isPresent()){
            task.get().setDone(true);
            taskRepositoy.save(task.get());
            return true;
        }

        return false;
    }

    @Override
    public void deleteTask(Long id) {
        taskRepositoy.deleteById(id);
    }

    @Override
    public List<Task> finByUser(User user) {
        return  taskRepositoy.findByUser(user);
    }
}

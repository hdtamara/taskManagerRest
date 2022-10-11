package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface TaskRepositoy extends CrudRepository<Task,Long> {
    List<Task> findByUser(User user);
}

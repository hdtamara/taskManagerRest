package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.entities.User;

import java.util.List;

public interface IUserService   {
    public List<User> findAll();

    public User finById(Long id);

    public User createUser(User user);

    public void updateUSer(Long id);

    public void deleteUser(Long id);


}

package com.taskmanagerrest.taskmanager.services;


import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;

import java.util.List;



public interface IUserService   {
    public List<User> findAll();

    public User finById(Long id) throws UserNotFoundException;

    public User createUser(UserDto user) throws UserAlreadyExistsException;

    public void updateUSer(User user);

    public void deleteUser(Long id) throws UserNotFoundException;    

    public List<User> findByEnabled(boolean status);

    public User findByEmail(String email);


}

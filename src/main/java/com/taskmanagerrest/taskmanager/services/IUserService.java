package com.taskmanagerrest.taskmanager.services;


import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;

import java.util.List;

import javax.security.auth.login.CredentialException;

public interface IUserService   {
    public List<User> findAll();

    public User finById(Long id) throws UserNotFoundException;

    public User createUser(UserDto user) throws UserAlreadyExistsException;

    public void updateUSer(Long id);

    public void deleteUser(Long id) throws UserNotFoundException;

    public User checkCredentiales(User user) throws UserNotFoundException, CredentialException, UserNotEnabledException;

    public List<User> findByEnabled(boolean status);


}

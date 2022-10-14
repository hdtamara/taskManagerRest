package com.taskmanagerrest.taskmanager.services;


import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.Rol;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.enums.RolesList;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.repository.RolRepository;
import com.taskmanagerrest.taskmanager.repository.UserRepository;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.security.auth.login.CredentialException;

@Service
public class UserService implements  IUserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    @Override
    public List<User> findAll() {

        return (List<User>) userRepository.findAll();
    }

    @Override
    public User finById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("user not found with id : "+ id);

        return user.get();
    }

    @Override
    public User createUser(UserDto user) throws UserAlreadyExistsException {
        User EmailUser = userRepository.findByEmail(user.getEmail());
        if(EmailUser!=null){
            throw new UserAlreadyExistsException("User already exists with email: "+user.getEmail());
        }
        User newUser = User.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .image(user.getImage())
                .password(user.getPassword())
                .build();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolRepository.findByRoleName(RolesList.ROL_USER).get());
        if (user.getRoles().contains("admin"))
            roles.add(rolRepository.findByRoleName(RolesList.ROL_ADMIN).get());
        newUser.setRoles(roles);
        System.out.println(newUser);
        return userRepository.save(newUser);
        
    }

    @Override
    public void updateUSer(Long id) {

    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<User> userDelete = userRepository.findById(id);
        if(userDelete.isEmpty())
            throw new UserNotFoundException("user not found with id : "+ id);
        
        userDelete.get().setEnabled(false);
        userRepository.save(userDelete.get());

    }

    @Override
    public User checkCredentiales(User user) throws UserNotFoundException,CredentialException, UserNotEnabledException {
        User userLogin = userRepository.findByEmail(user.getEmail());
        if(userLogin==null){
            throw new UserNotFoundException("user not found with email : "+ user.getEmail());
        }

        if(!userLogin.isEnabled()){
            throw new UserNotEnabledException("user not enabled with email : "+ user.getEmail());
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(userLogin.getPassword(), user.getPassword())){
            return userLogin;
        };

        throw new CredentialException("Wrong credentials");
        
    }

    @Override
    public List<User> findByEnabled(boolean status) {
        
        return  (List<User>) userRepository.findByEnabled(status);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

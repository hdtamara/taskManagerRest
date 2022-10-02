package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements  IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {

        return (List<User>) userRepository.findAll();
    }

    @Override
    public User finById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            return null;

        return user.get();
    }

    @Override
    public User createUser(User user) {
        User EmailUser = userRepository.findByEmail(user.getEmail());
        if(EmailUser!=null){
            return null;
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void updateUSer(Long id) {

    }

    @Override
    public void deleteUser(Long id) {
        User user = finById(id);
        userRepository.delete(user);

    }
}

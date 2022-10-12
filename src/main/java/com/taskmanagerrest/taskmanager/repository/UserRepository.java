package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     User findByEmail(String email);
     List<User> findByEnabled(boolean status);
     


}

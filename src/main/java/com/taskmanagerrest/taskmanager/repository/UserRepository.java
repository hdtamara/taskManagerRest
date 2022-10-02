package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
     User findByEmail(String email);


}

package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.Rol;
import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.enums.RolesList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TaskRepositoyTest {

    @Autowired
    TaskRepositoy taskRepositoy;
    @Autowired
    UserRepository userRepository;

    private  User user;
    private User user2;
    private  Rol rol;

    @BeforeEach
    void setUp() {
        Set<Rol> rolesUser= new HashSet<>();
        rol = Rol.builder().id(1L).roleName(RolesList.ROL_ADMIN).build();
        rolesUser.add(rol);
        user = User.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .build();
        user2 = User.builder()
                .name("Camila")
                .lastName("Pacheco")
                .email("Camila@correo.com")
                .password("123456")
                .enabled(true)
                .build();

        user.setRoles(rolesUser);
        user2.setRoles(rolesUser);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        userRepository.saveAll(users);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        taskRepositoy.deleteAll();
    }

    @Test
    void findByUser() {
        //give
        Task task1 = Task.builder()
                .description("Hacer tareas")
                .done(false)
                .finishDate(LocalDate.ofEpochDay(2022/10/24))
                .user(user)
                .build();
        Task task2 = Task.builder()
                .description("Hacer tareas")
                .done(false)
                .finishDate(LocalDate.ofEpochDay(2022/10/24))
                .user(user2)
                .build();
        taskRepositoy.save(task1);
        taskRepositoy.save(task2);
        //When
        List<Task> tasksByUsers = taskRepositoy.findByUser(user);
        List<Task> tasksByUsers2 = taskRepositoy.findByUser(user2);
        //then
        assertThat(tasksByUsers.get(0)).isEqualTo(task1);
        assertThat(tasksByUsers2.get(0)).isEqualTo(task2);




    }
}
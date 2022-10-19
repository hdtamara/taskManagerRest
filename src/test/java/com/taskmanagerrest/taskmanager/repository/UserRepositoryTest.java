package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.Rol;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.enums.RolesList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired RolRepository rolRepository;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        Set<Rol> rolesUser= new HashSet<>();
       Rol rol = Rol.builder().id(1L).roleName(RolesList.ROL_ADMIN).build();
        rolesUser.add(rol);
        rolRepository.save(rol);
        user = User.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .roles(rolesUser)
                .build();
        user2 = User.builder()
                .name("Camila")
                .lastName("Pacheco")
                .email("Camila@correo.com")
                .password("123456")
                .enabled(true)
                .roles(rolesUser)
                .build();
            user3 = User.builder()
                .name("Patricia")
                .lastName("Pacheco")
                .email("Patricia@correo.com")
                .password("123456")
                .enabled(false)
                .roles(rolesUser)
                .build();


    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("Test para buscar usuarios por email")
    @Test
    void findByEmail() {
        //given
        userRepository.save(user);
        //when
        User userExist = userRepository.findByEmail("alejandro@correo.com");
        //then
        assertThat(userExist).isNotNull();

    }

    @DisplayName("Test para buscar usuario por estado activo")
    @Test
    void findByEnabledTrue() {
        //given

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        userRepository.saveAll(users);
        List<User> usersEnabled = new ArrayList<>();
        usersEnabled.add(user);
        usersEnabled.add(user2);
        //when
        List<User> usersByEnabled = userRepository.findByEnabled(true);
        //then
        assertThat(usersByEnabled).isEqualTo(usersEnabled);

    }

    @DisplayName("Test para buscar usuario por estado inactivo")
    @Test
    void findByEnabledFalse() {
        //given

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        userRepository.saveAll(users);
        List<User> usersDisabled = new ArrayList<>(); ;
        usersDisabled.add(user3);
        //when
        List<User> usersByEnabled = userRepository.findByEnabled(false);
        //then
        assertThat(usersByEnabled).isEqualTo(usersDisabled);

    }
}
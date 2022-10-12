package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.dto.UserDto;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.UserAlreadyExistsException;
import com.taskmanagerrest.taskmanager.exception.UserNotEnabledException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.repository.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    @InjectMocks
    private UserService userService;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .build();
        user2 = User.builder()
                .id(2)
                .name("Camila")
                .lastName("Pacheco")
                .email("Camila@correo.com")
                .password("123456")
                .enabled(false)
                .build();
        user3 = User.builder()
                .id(2)
                .name("Rosio")
                .lastName("Pacheco")
                .email("Rosio@correo.com")
                .password("123456")
                .enabled(true)
                .build();
    }
    @DisplayName("Test para buscar todos los registros")
    @Test
    void findAll() {
        //given
        given(userRepository.findAll()).willReturn(List.of(user,user2));


        //when
        List<User> users = userService.findAll();

        //then
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
        assertThat(users).isEqualTo(List.of(user,user2));
    }

    @DisplayName("Test para buscar un usuario por id")
    @Test
    void finById() throws UserNotFoundException {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.findById(2L)).willReturn(Optional.of(user2));
        //when
        User userById = userService.finById(1L);
        User userById2 = userService.finById(2L);

        //then
        assertThat(userById).isEqualTo(user);
        assertThat(userById2).isEqualTo(user2);
    }

    @DisplayName("Test para id no encontrada")
    @Test
    void finByIdWhenIdNotFound() throws UserNotFoundException {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThatThrownBy(()->userService.finById(1L))
                .isInstanceOf(UserNotFoundException.class)//then
                .hasMessageContaining("user not found with id : "+ 1);

    }

    @DisplayName("Test para crear un usuario")
    @Test
    void createUser() throws UserAlreadyExistsException {
        User userToSave = User.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .build();

        UserDto userDto = UserDto.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .build();
        //given
        given(userRepository.findByEmail(userToSave.getEmail())).willReturn(null);
        given(userRepository.save(userToSave)).willReturn(userToSave);
        //when
        User userCreate = userService.createUser(userDto);

        //then
        assertThat(userCreate).isNotNull();


    }

    @DisplayName("Test para cuando el email Existe")
    @Test
    void createUserwhenEmailExist() throws UserAlreadyExistsException {
        User userToSave = User.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .build();

        UserDto userDto = UserDto.builder()
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .build();
        //given
        given(userRepository.findByEmail(userToSave.getEmail())).willReturn(userToSave);

        //when
        assertThatThrownBy(()->userService.createUser(userDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User already exists with email: "+userDto.getEmail());
        //then
        verify(userRepository,never()).save(userToSave);


    }

    @Test
    void updateUSer() {
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        //given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));

        //when
        userService.deleteUser(userId);

        //then
        assertThat(user.isEnabled()).isFalse();
        verify(userRepository,times(1)).save(user);



    }

    @Test
    void deleteUserWhenUserNotExist() throws UserNotFoundException {
        //given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //when

        assertThatThrownBy(()->userService.deleteUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user not found with id : "+userId);

        //then
        verify(userRepository,never()).save(any());



    }

    @Test
    void checkCredentiales() throws UserNotFoundException, CredentialException, UserNotEnabledException {
        //given
        given(userRepository.findByEmail(anyString())).willReturn(user);
        given(argon2.verify(anyString(),anyString())).getMock();
        //when
        assertThatThrownBy(()->userService.checkCredentiales(user))
                .isInstanceOf(CredentialException.class)
                //then
                .hasMessageContaining("Wrong credentials");

    }

    @Test
    void checkCredentialesUserNotFound() throws UserNotFoundException, CredentialException, UserNotEnabledException {
        //given

        given(userRepository.findByEmail(anyString())).willReturn(null);

        //when

        assertThatThrownBy(()->userService.checkCredentiales(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user not found with email : "+ user.getEmail());
        //then
        verify(argon2,never()).verify(anyString(),anyString());

    }

    @Test
    void checkCredentialesUserNotEnabled() throws UserNotFoundException, CredentialException, UserNotEnabledException {
        //given

        given(userRepository.findByEmail(anyString())).willReturn(user2);


        //when

        assertThatThrownBy(()->userService.checkCredentiales(user2))
                .isInstanceOf(UserNotEnabledException.class)
                .hasMessageContaining("user not enabled with email : "+ user2.getEmail());
        //then
        verify(argon2,never()).verify(anyString(),anyString());

    }

    @Test
    void findByEnabledTrue() {

        //given
        boolean enabled = true;
        given(userRepository.findByEnabled(true)).willReturn(List.of(user,user3));
        //when
        List<User> usersEnables = userService.findByEnabled(enabled);
        //then
        assertThat(usersEnables).isNotNull();
        assertThat(usersEnables).isEqualTo(List.of(user,user3));
    }

    @Test
    void findByEnabledFalse() {

        //given
        boolean enabled = false;
        given(userRepository.findByEnabled(false)).willReturn(List.of(user2));
        //when
        List<User> usersEnables = userService.findByEnabled(enabled);
        //then
        assertThat(usersEnables).isNotNull();
        assertThat(usersEnables).isEqualTo(List.of(user2));
    }
}
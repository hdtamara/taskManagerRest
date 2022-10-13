package com.taskmanagerrest.taskmanager.services;

import com.taskmanagerrest.taskmanager.dto.TaskDto;
import com.taskmanagerrest.taskmanager.entities.Task;
import com.taskmanagerrest.taskmanager.entities.User;
import com.taskmanagerrest.taskmanager.exception.TaskNotFoundException;
import com.taskmanagerrest.taskmanager.exception.UserNotFoundException;
import com.taskmanagerrest.taskmanager.repository.TaskRepositoy;
import com.taskmanagerrest.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepositoy taskRepositoy;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  TaskService taskService;

    private Task task1;
    private  Task task2;
    private  User user1;
    private  User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1)
                .name("Alejandro")
                .lastName("Fernandez")
                .email("alejandro@correo.com")
                .password("123456")
                .enabled(true)
                .build();

        task1 = Task.builder()
                .description("Hacer ejercicio")
                .finishDate(LocalDate.parse("2022-11-01"))
                .user(user1)
                .done(false)
                .build();
        task2 = Task.builder()
                .finishDate(LocalDate.parse("2022-11-01"))
                .description("Hacer tareas")
                .user(user2)
                .done(false)
                .build();

    }

    @Test
    void findAll() {
        //give
        given(taskRepositoy.findAll()).willReturn(List.of(task1,task2));
        //when
        List<Task> tasks = taskService.findAll();

        //then
        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks).isEqualTo(List.of(task1,task2));
    }

    @Test
    void createTask() throws UserNotFoundException {
        Task task = Task.builder()
                .description("Hacer ejercicio")
                .finishDate(LocalDate.parse("2022-11-01"))
                .user(user1)
                .done(false)
                .build();

        TaskDto taskDto = TaskDto.builder()
                .description("Hacer ejercicio")
                .finishDate(LocalDate.parse("2022-11-01"))
                .userId(1L)
                .build();
        //given
        given(taskRepositoy.save(task)).willReturn(task);
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user1));

        //when
        Task newTask = taskService.createTask(taskDto);

        //then
        assertThat(newTask).isNotNull();
        verify(taskRepositoy,times(1)).save(any());
    }

    @Test
    void createTaskWhenUserNotFount() throws UserNotFoundException {
        Task task = Task.builder()
                .description("Hacer ejercicio")
                .finishDate(LocalDate.parse("2022-11-01"))
                .user(user1)
                .done(false)
                .build();

        TaskDto taskDto = TaskDto.builder()
                .description("Hacer ejercicio")
                .finishDate(LocalDate.parse("2022-11-01"))
                .userId(1L)
                .build();
        //given
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(()->taskService.createTask(taskDto))
                .isInstanceOf(UserNotFoundException.class)
                //then
                .hasMessageContaining("user not found with id : "+user1.getId());
        verify(userRepository,never()).save(any());
    }

    @Test
    void updatTask() throws TaskNotFoundException {
        //given
        long taskId = 1;
        given(taskRepositoy.findById(taskId)).willReturn(Optional.of(task1));
        //when
        taskService.updatTask(taskId);
        //then
        assertThat(task1.isDone()).isTrue();
        verify(taskRepositoy,times(1)).save(any());
    }

    @Test
    void updatTaskWhenTaskNotFound() throws TaskNotFoundException {
        //given
        long taskId = 1;
        given(taskRepositoy.findById(taskId)).willReturn(Optional.empty());
        //when
        assertThatThrownBy(()->taskService.updatTask(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                //then
                .hasMessageContaining("task not found with id : "+taskId);
        verify(taskRepositoy,never()).save(any());
    }

    @Test
    void deleteTask() throws TaskNotFoundException {
        //given
        long taskId = 1;
        given(taskRepositoy.findById(taskId)).willReturn(Optional.of(task1));
        willDoNothing().given(taskRepositoy).deleteById(taskId);
        //when
        taskService.deleteTask(taskId);
        //then
        verify(taskRepositoy,times(1)).deleteById(anyLong());

    }

    @Test
    void deleteTaskWhenTaskNotFound() throws TaskNotFoundException {
        //given
        long taskId = 1;
        given(taskRepositoy.findById(taskId)).willReturn(Optional.empty());
        //willDoNothing().given(taskRepositoy).deleteById(taskId);
        //when
        assertThatThrownBy(()->taskService.deleteTask(taskId))
                .isInstanceOf(TaskNotFoundException.class)
                //then
                .hasMessageContaining("task not found with id : "+taskId);


        //then
        verify(taskRepositoy,never()).deleteById(anyLong());

    }

    @Test
    void finByUser() throws UserNotFoundException {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        given(taskRepositoy.findByUser(user1)).willReturn(List.of(task1));
        //when
        List<Task> tasks = taskService.finByUser(user1);
        //then
        assertThat(tasks).isNotNull();
        assertThat(tasks).isEqualTo(List.of(task1));
    }

    @Test
    void finByUserWhenUserNotExist() throws UserNotFoundException {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.empty());
        //when

        //then
        assertThatThrownBy(()->taskService.finByUser(user1))
                .isInstanceOf(UserNotFoundException.class)
                //then
                .hasMessageContaining("user not found with id : "+user1.getId());
        verify(taskRepositoy,never()).findByUser(any());
    }
}
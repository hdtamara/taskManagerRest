package com.taskmanagerrest.taskmanager.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class TaskDto {
    @NotBlank(message = "Description shouldn't be empty")
    private String description;
    @NotBlank(message = "Finish Date shouldn't be empty")
    private LocalDate finishDate; 
    @NotBlank(message = "User  shouldn't be empty")
    private Long userId;
}

package com.taskmanagerrest.taskmanager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class UserEditDto {
    @NotBlank(message =  "name shouldn't be empty")    
    private String name;
    private  String lastName;
    @NotBlank(message = "Email shouldn't be empty")
    @Email(message = "invalid email address")   
    private  String email;
    private  String image;
    
}

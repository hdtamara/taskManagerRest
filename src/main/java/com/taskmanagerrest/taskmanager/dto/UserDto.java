package com.taskmanagerrest.taskmanager.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class UserDto {
    @NotBlank(message =  "name shouldn't be empty")    
    private String name;
    private  String lastName;
    @NotBlank(message = "Email shouldn't be empty")
    @Email(message = "invalid email address")   
    private  String email;
    private  String image;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 10, message = "Passwor shouldn't be < 10 caracteres")   
    private String password;
}

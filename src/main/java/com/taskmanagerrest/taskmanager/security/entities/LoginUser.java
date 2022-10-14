package com.taskmanagerrest.taskmanager.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginUser {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

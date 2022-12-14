package com.taskmanagerrest.taskmanager.security.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
public class JwtDto {
    private  String token;
    private String beares = "Bearer ";
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.email = nombreUsuario;
        this.authorities = authorities;
    }
}

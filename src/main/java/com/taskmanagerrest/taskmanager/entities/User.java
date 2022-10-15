package com.taskmanagerrest.taskmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor(staticName = "build")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;          
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name")
    private  String lastName;         
    @Column(name = "email", nullable = false, unique = true)
    private  String email;
    @Column(name = "image")
    private  String image;   
    @Column(name = "password")    
    private String password;
    @Column(name="status") @Builder.Default
    private boolean enabled=true;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles= new HashSet<>();;

}

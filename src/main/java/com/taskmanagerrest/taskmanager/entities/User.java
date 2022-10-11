package com.taskmanagerrest.taskmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;





@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor(staticName = "build")
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
    @Column(name="status")
    private boolean enabled=true;

}

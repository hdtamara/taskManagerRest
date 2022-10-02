package com.taskmanagerrest.taskmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "done", nullable = false)
    private boolean done;
    @Column(name="date_finish", nullable = false)
    private LocalDate dateFinish;
    @Column(name = "date_finish_real")
    private LocalDate dateFinishReal;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

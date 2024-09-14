package com.example.task_manegement_web_application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "userEntity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "authorities", nullable = false)
    private ERole eRole = ERole.ROLE_USER;


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Task> tasks;


}

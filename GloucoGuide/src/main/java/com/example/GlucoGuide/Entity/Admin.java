package com.example.GlucoGuide.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Article> Articles = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reviews> reviews = new ArrayList<>();

}

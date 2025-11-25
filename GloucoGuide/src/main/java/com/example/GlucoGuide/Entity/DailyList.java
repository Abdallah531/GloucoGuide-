package com.example.GlucoGuide.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class DailyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    @JsonBackReference
    private UserAccount user;

    @OneToMany(mappedBy = "dailyList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Meal> meals = new ArrayList<>();

    @OneToMany(mappedBy = "dailyList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Medication> medications = new ArrayList<>();

    @OneToMany(mappedBy = "dailyList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Exercise> exercises = new ArrayList<>();
}

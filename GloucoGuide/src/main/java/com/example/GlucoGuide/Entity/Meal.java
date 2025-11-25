package com.example.GlucoGuide.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalTime;

@Data
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalTime time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "daily_list_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private DailyList dailyList;
}
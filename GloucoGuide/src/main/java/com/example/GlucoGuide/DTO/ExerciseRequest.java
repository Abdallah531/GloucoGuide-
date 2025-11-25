package com.example.GlucoGuide.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ExerciseRequest {
    private String name;
    private LocalTime time;
    private int durationMinutes;
    private LocalDate date;
}

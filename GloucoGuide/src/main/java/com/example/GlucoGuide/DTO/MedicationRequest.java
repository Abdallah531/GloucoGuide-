package com.example.GlucoGuide.DTO;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MedicationRequest {
    private String name;
    private String dosage;
    private LocalDate date;
    private LocalTime time;
}

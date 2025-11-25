package com.example.GlucoGuide.DTO;

import com.example.GlucoGuide.Entity.DailyList;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MealRequest {
    private String name;

    private LocalDate date;
    private LocalTime time;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "daily_list_id", referencedColumnName = "id", nullable = false)
//    private DailyList dailyList;
}
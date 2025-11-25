package com.example.GlucoGuide.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class ReviewsDTO {
    private Long id;
    private int value;
    private String feedback;
    private Date date;
    private Long userId;
    private String userName;
    private Long adminId;
    private String adminName;
}

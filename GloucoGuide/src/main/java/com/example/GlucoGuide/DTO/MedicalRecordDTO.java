package com.example.GlucoGuide.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {
    private Long recordId;
    private int measurements;
    private String note;
    private Date date;
    private Long userId;
    private Long recordTypeId;
    private String advice;

}
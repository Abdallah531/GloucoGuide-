package com.example.GlucoGuide.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private boolean readFlag;
    private Date date;
    private String message;

    public void markAsRead() {
        this.readFlag = true;
    }
}
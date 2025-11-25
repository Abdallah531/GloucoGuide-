package com.example.GlucoGuide.Converter;

import com.example.GlucoGuide.DTO.NotificationDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.Notification;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {

    @Autowired
    private UserService userService;

    public NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setUserId(notification.getUser().getUserId());
        notificationDTO.setReadFlag(notification.isRead_flag());
        notificationDTO.setDate(notification.getDate());
        notificationDTO.setMessage(notification.getMessage());
        return notificationDTO;
    }

    public Notification convertToEntity(NotificationDTO notificationDTO, UserAccountDTO user) {
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setUser(userService.getUserById(user.getUserId()));
        notification.setRead_flag(notificationDTO.isReadFlag());
        notification.setDate(notificationDTO.getDate());
        notification.setMessage(notificationDTO.getMessage());
        return notification;
    }
}
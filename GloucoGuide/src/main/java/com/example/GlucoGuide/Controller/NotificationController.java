package com.example.GlucoGuide.Controller;


import com.example.GlucoGuide.DTO.NotificationDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.Notification;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;



    @GetMapping("/")
    public ResponseEntity<?> getUserNotifications(HttpSession session) {

        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userDTO.getUserId());
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long notificationId, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        NotificationDTO notification = notificationService.getNotificationById(notificationId);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        notification.markAsRead();

        NotificationDTO notification11 = notificationService.updateNotification(notification, userDTO);
        return ResponseEntity.ok(notification11);
    }


    @PostMapping("/all/read")
    public ResponseEntity<?> markAllNotificationAsRead( HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userDTO.getUserId());

        for (NotificationDTO notification : notifications){

            notification.markAsRead();
            notificationService.updateNotification(notification, userDTO);
        }
        List<NotificationDTO> notifications1 = notificationService.getNotificationsForUser(userDTO.getUserId());

        return ResponseEntity.ok(notifications1);
    }
}

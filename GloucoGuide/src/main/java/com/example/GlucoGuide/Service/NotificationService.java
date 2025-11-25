package com.example.GlucoGuide.Service;



import com.example.GlucoGuide.Converter.NotificationConverter;
import com.example.GlucoGuide.DTO.NotificationDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Entity.Notification;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationConverter notificationConverter;

    // Method to retrieve notifications for a specific user
    public List<Notification> getNotificationsForUser(UserAccount user) {
        // Implement logic to fetch notifications for the user from the repository
        return notificationRepository.findByUser(user);
    }

    // Method to save a new notification
    public void createNotification(UserAccountDTO user, String message) {
        NotificationDTO notification = new NotificationDTO();
        //notification.setUserId(user.getUserId());
        notification.setMessage(message);
        notification.setDate(new Date());
        Notification notification1 = notificationConverter.convertToEntity(notification,user);
        notificationRepository.save(notification1);
    }
    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserUserId(userId).stream()
                .map(notificationConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO getNotificationById(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        assert notification != null;
        return notificationConverter.convertToDTO(notification);
    }

    public NotificationDTO updateNotification(NotificationDTO notification, UserAccountDTO userDTO) {
        Notification notification1 = notificationConverter.convertToEntity(notification,userDTO);
        notificationRepository.save(notification1);
        return notification;
    }
}

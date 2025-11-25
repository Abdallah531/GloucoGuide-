package com.example.GlucoGuide.Repository;


import com.example.GlucoGuide.Entity.Notification;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(UserAccount user);
    List<Notification> findByUserUserId(Long userId);
}

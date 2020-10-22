package com.board_of_ads.repository;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    List<UserNotification> getUserNotificationsByUser (User user);

  //  @Query("select n from UserNotification n  ORDER BY n.sentTime DESC")
    @Query("select n from UserNotification n ")

    List<UserNotification> getAll();

    UserNotification findById_NotificationIdAndId_UserId(Long notificationId, Long userId);

    UserNotification getAllByNotificationAndUser(Notification notification, User user);

    UserNotification findById(UserNotificationId id);
}
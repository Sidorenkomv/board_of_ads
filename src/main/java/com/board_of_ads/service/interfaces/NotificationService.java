package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    void createNotification(Notification notification);
    List<UserNotification> getUsersAllNotifications(User user);
    List<UserNotification> getAllNotifications();
    boolean sendNotificationToUsers(Notification notification, List<User> users);
    boolean updateUserNotificationFields(UserNotification userNotification);
    UserNotification findByNoteIdAndUserId(Long noteId, Long userId);
    boolean deleteUserNotification(UserNotification userNotification);
}

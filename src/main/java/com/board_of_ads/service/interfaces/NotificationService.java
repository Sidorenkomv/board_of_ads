package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    void createNotification(Notification notification);
    List<UserNotification> getUsersAllNotifications(User user);
    List<UserNotification> getAllNotifications();
    void sendNotificationToUsers(Notification notification, List<User> users);
    boolean updateUserNotificationFields(UserNotification userNotification);
    UserNotification findByNoteIdAndUserId(Long noteId, Long userId);
    boolean deleteUserNotification(UserNotification userNotification);
    int[] getUsersNotificationsCountMap(User user);

    List<NotificationDto> getNotificationDtoOfUser (User user);
    boolean changeStatusToRead(Long noteId, User user);
    boolean deleteNotificationFromUser (Long noteId, User user);
}

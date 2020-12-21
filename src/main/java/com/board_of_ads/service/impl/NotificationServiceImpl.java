package com.board_of_ads.service.impl;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.NotificationDto;
import com.board_of_ads.repository.NotificationRepository;
import com.board_of_ads.repository.UserNotificationRepository;
import com.board_of_ads.service.interfaces.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final UserNotificationRepository userNotificationRepository;
    @Autowired
    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(Notification notification) {
        log.debug("In Method createNotification");
        try {
            notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Exception occur while save Notification ", e);
        }
    }

    @Override
    public void sendNotificationToUsers(Notification notification, List<User> users) {
        log.debug("In Method send Notification To User");
        for (User thisUser : users) {
            try {
                userNotificationRepository.save(new UserNotification(notification, thisUser));
            } catch (Exception e) {
                log.error("Exception occur while save Notification ", e);
                return;
            }
        }
    }

    @Override
    public List<UserNotification> getUsersAllNotifications(User user) {
        log.debug("Get all notifications of {} - user, user.id = {} ", user.getFirstName(), user.getId());
        try {
            return userNotificationRepository.getUserNotificationsByUser(user);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<UserNotification> getAllNotifications() {
        log.debug("Get all notifications in Data base ");
        try {
            return userNotificationRepository.getAll();
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return null;
        }
    }

    @Override
    public UserNotification findByNoteIdAndUserId(Long noteId, Long userId) {
        log.debug(" in findByNoteIdAndUserId(Long noteId, Long userId) ...");
        return userNotificationRepository.findById_NotificationIdAndId_UserId(noteId, userId);
    }

    @Override
    public boolean updateUserNotificationFields(UserNotification userNote) {
        try {
            notificationRepository.save(userNote.getNotification());
            userNotificationRepository.save(userNote);
            return true;
        } catch (Exception e) {
            log.error("Exception occur while trying to update UserNotification ", e);
            return false;
        }
    }

    @Override
    public boolean deleteUserNotification(UserNotification userNotification) {
        try {
            userNotificationRepository.delete(userNotification);
            return true;
        } catch (Exception e) {
            log.error("Exception occur while trying to update UserNotification ", e);
            return false;
        }
    }

    @Override
    public int[] getUsersNotificationsCountMap(User user) {
        int[] countMap = new int[3];
        List<UserNotification> userNotifications = getUsersAllNotifications(user);
        if (userNotifications.size() > 0) {
            countMap[0] = userNotifications.size();
            for (UserNotification un : userNotifications) {
                if (un.getStatus().equals("newSent")) {
                    countMap[1]++;
                }
            }
            countMap[2] = countMap[0] - countMap[1];
        }
        return countMap;
    }

    public boolean changeStatusToRead(Long noteId, User user) {
        boolean var = false;
        UserNotification un = findByNoteIdAndUserId(noteId, user.getId());
        if (un != null) {
            un.setStatus("read");
            var = updateUserNotificationFields(un);
        }
        return var;
    }

    public List<NotificationDto> getNotificationDtoOfUser (User user) {
        return notificationRepository.findAllUserNotificationDto(user.getId());

    }

    public boolean deleteNotificationFromUser (Long noteId, User user) {
        UserNotification un = findByNoteIdAndUserId(noteId, user.getId());
        return un != null && deleteUserNotification(un);
    }

}

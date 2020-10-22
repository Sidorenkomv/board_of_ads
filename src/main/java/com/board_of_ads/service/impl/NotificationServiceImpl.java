package com.board_of_ads.service.impl;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.repository.NotificationRepository;
import com.board_of_ads.repository.UserNotificationRepository;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final UserNotificationRepository userNotificationRepository;
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final UserService userService;

    @Override
    public void createNotification(Notification notification) {
        log.info("In Method createNotification");
        try {
            notificationRepository.save(notification);
            log.info("a {} notification created!", notification.getMessageTitle());
        } catch (Exception e) {
            log.error("Exception occur while save Notification ", e);
        }
    }

    @Override
    public boolean sendNotificationToUsers(Notification notification, List<User> users) {
        log.info("In Method send Notification To User");
        for (User thisUser : users) {
            try {
                userNotificationRepository.save(new UserNotification(notification, thisUser));
            } catch (Exception e) {
                log.error("Exception occur while save Notification ", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public List<UserNotification> getUsersAllNotifications(User user) {
        log.info("Get all notifications of {} - user, user.id = {} ", user.getFirsName(), user.getId());
        try {
            return userNotificationRepository.getUserNotificationsByUser(user);
        } catch (Exception e) {
               log.error("Exception occur while fetch Notification by User ", e);
               return new ArrayList<>();
        }
    }

    @Override
    public List<UserNotification> getAllNotifications() {
        log.info("Get all notifications in Data base ");
        try {
            return userNotificationRepository.getAll();
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return null;
        }

    }

    @Override
    public UserNotification findByNoteIdAndUserId(Long noteId, Long userId){
        log.info(" in findByNoteIdAndUserId(Long noteId, Long userId) ...");
        return userNotificationRepository.findById_NotificationIdAndId_UserId(noteId, userId);
    }

    @Override
    public boolean updateUserNotificationFields(UserNotification userNote){
        try{
        notificationRepository.save(userNote.getNotification());
        userNotificationRepository.save(userNote);
        return true;
        } catch (Exception e) {
            log.error("Exception occur while trying to update UserNotification ", e);
            return false;
        }
    }

    @Override
    public boolean deleteUserNotification(UserNotification userNotification){
        try{
            userNotificationRepository.delete(userNotification);
            return true;
        } catch (Exception e) {
            log.error("Exception occur while trying to update UserNotification ", e);
            return false;
        }
    }
}

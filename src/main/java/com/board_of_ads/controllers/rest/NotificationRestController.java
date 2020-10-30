package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.NotificationDto;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    public NotificationRestController(){}

    @GetMapping(value="/user/{id}",  produces="application/json")
    public Response<List<NotificationDto>> getNotificationsOfUser(@PathVariable("id") Long id){
        log.info("In NotificationRestController: inside getNotificationsOfUser api-method for fetch user id {} notification ", id);
        User user = userService.getUserById(id);
        List<UserNotification> userNotifications = notificationService.getUsersAllNotifications(user);
        if ( userNotifications.size() > 0 ) {
            List<NotificationDto> notificationsResponseList = new ArrayList<>();
            for (UserNotification userNote: userNotifications) {
                notificationsResponseList.add(new NotificationDto(userNote));
            }
            return Response.ok(notificationsResponseList);
        } else {
            return  new ErrorResponse<>(new Error(204, "Notifications not found"));
        }
    }

    @PatchMapping(value="/user/{userId}/{noteId}/change-status-to-read")
    public Response<Void> changeStatusToRead(@PathVariable Long userId, @PathVariable Long noteId){
        log.info("inside api changeStatusToRead ");
        UserNotification un = notificationService.findByNoteIdAndUserId(noteId, userId);
        if (un != null) {
            un.setStatus("read");
            notificationService.updateUserNotificationFields(un);
            return new Response<>();
        }
        return new ErrorResponse<>(new Error(204, "Notification not found"));
    }

    @DeleteMapping(value="/user/{userId}/{noteId}/delete")
    public Response<Void> deleteNotificationFromUser(@PathVariable Long userId, @PathVariable Long noteId) {
        log.info("inside api deleteNotificationFromUser ");
        UserNotification un = notificationService.findByNoteIdAndUserId(noteId, userId);
        if (un != null) {
            notificationService.deleteUserNotification(un);
            return new Response<>();
        }
        return new ErrorResponse<>(new Error(204, "Notification not found"));
    }

    @GetMapping(value="/user/{id}/count-map",  produces="application/json")
    public Response<int[]> getNotificationsCountOfUser(@PathVariable("id") Long id){
        log.info("In getNotificationsCountOfUser api-method for fetch and count user id {} notification ", id);
        User user = userService.getUserById(id);
        int[] countMap = notificationService.getUsersNotificationsCountMap(user);
            return  Response.ok(countMap);
    }
}


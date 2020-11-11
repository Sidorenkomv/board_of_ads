package com.board_of_ads.models.dto;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class NotificationDto {
    private Long acceptNumber;
    private Long notificationId;
    private Long userId;
    private String messageTitle;
    private String messageBody;
    private String clickAction;
    private String sentTime;
    private String status;
    private int urgentLevel;

    public NotificationDto(UserNotification userNote) {
        acceptNumber = userNote.getAcceptNumber();
        notificationId = userNote.getNotification().getId();
        userId = userNote.getUser().getId();
        messageTitle = userNote.getNotification().getMessageTitle();
        messageBody = userNote.getNotification().getMessageBody();
        clickAction = userNote.getNotification().getClickAction();
        status = userNote.getStatus();
        urgentLevel = userNote.getUrgentLevel();
        sentTime = convertTime(userNote.getSentTime());

    }

    public NotificationDto(UserNotification userNote, Notification notification) {
        notificationId = notification.getId();
        messageTitle = notification.getMessageTitle();
        messageBody = notification.getMessageBody();
        clickAction = notification.getClickAction();
        sentTime = convertTime(userNote.getSentTime());
        status = userNote.getStatus();
        urgentLevel = userNote.getUrgentLevel();
    }

    private String convertTime(LocalDateTime dateObj) {
        return dateObj.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

}

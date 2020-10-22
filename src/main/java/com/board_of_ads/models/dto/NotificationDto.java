package com.board_of_ads.models.dto;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.models.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificationDto {
    private Long acceptNumber;
    private Long notificationId;
    private Long userId;
    private String messageTitle;
    private String messageBody;
    private String clickAction;
    private LocalDateTime sentTime;
    private String status;
    private int urgentLevel;

    public NotificationDto(UserNotification userNote) {
        acceptNumber = userNote.getAcceptNumber();
        notificationId = userNote.getNotification().getId();
        userId = userNote.getUser().getId();
        messageTitle = userNote.getNotification().getMessageTitle();
        messageBody = userNote.getNotification().getMessageBody();
        clickAction = userNote.getNotification().getClickAction();
        sentTime = userNote.getSentTime();
        status = userNote.getStatus();
        urgentLevel = userNote.getUrgentLevel();
    }

    public NotificationDto(UserNotification userNote, Notification notification) {
        notificationId = notification.getId();
        messageTitle = notification.getMessageTitle();
        messageBody = notification.getMessageBody();
        clickAction = notification.getClickAction();
        sentTime = userNote.getSentTime();
        status = userNote.getStatus();
        urgentLevel = userNote.getUrgentLevel();
    }

}

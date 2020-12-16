package com.board_of_ads.models.dto;

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
    private String status;
    private int urgentLevel;
    private LocalDateTime sentTime;

    public String getSentTime() {
        return sentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

}

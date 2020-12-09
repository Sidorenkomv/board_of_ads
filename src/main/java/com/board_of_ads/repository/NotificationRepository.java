package com.board_of_ads.repository;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.MessageDto;
import com.board_of_ads.models.dto.NotificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select new com.board_of_ads.models.dto.NotificationDto (un.acceptNumber, un.notification.id, un.user.id, un.notification.messageTitle, un.notification.messageBody, un.notification.clickAction, un.status, un.urgentLevel, un.sentTime )from UserNotification un where un.user.id = :user_id")
    List<NotificationDto> findAllUserNotificationDto(@Param ("user_id") Long id);
}

package com.board_of_ads.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_notification")
public class UserNotification {

    private static long counter;

    @EmbeddedId
    private UserNotificationId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("notificationId")
    private Notification notification;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @Size(max = 50)
    @Column
    private String status = "newSent";		// ( newSent / read / deletedByUser)

    @Column
    private int urgentLevel = 1;

    @Column(name = "sent_time")
    @CreatedDate
    private LocalDateTime sentTime = LocalDateTime.now();

    @Column(name = "accept_number")
    private long acceptNumber;

    public UserNotification(Notification notification, User user) {
        this.user = user;
        this.notification = notification;
        this.id = new UserNotificationId(notification.getId(), user.getId());
        acceptNumber = ++counter;
    }

}


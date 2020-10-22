package com.board_of_ads.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"users"})
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserNotification> users = new ArrayList<>();

    @Size(min = 5, max = 90)
    @Column(name="message_title")
    private String messageTitle;

    @Size(min = 5, max = 255)
    @Column(name="message_body")
    private String messageBody;

    @Size(max = 255)
    @Column(name = "click_action")
    private String clickAction = "https://www.avito.ru"; // redirect user to this URL

    public Notification(String messageTitle, String message) {
        this.messageTitle = messageTitle;
        messageBody = message;
    }

    public void addUser(User user) {
        UserNotification userNote = new UserNotification(this, user);
        users.add(userNote);
        user.getNotifications().add(userNote);
    }

    public void removeUser(User user) {
        for (Iterator<UserNotification> iterator = users.iterator();
             iterator.hasNext(); ) {
            UserNotification userNote = iterator.next();

            if (userNote.getNotification().equals(this) &&
                    userNote.getUser().equals(user)) {
                iterator.remove();
                userNote.getUser().getNotifications().remove(userNote);
                userNote.setNotification(null);
                userNote.setUser(null);
            }
        }
    }



}


package com.board_of_ads.models.dto;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.Message;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.UserNotification;
import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.dto.review.Review;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String sessionID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Image avatar;
    private City city;
    private LocalDateTime dataRegistration;
    private boolean enable;
    private String roles;

    private String newPassword;
    private Long cityId;

    public UserDto(Long id, String sessionID, String email, String password, String firstName, String lastName, String phone, LocalDateTime dataRegistration, boolean enable) {
        this.id = id;
        this.sessionID = sessionID;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.dataRegistration = dataRegistration;
        this.enable = enable;
    }
}

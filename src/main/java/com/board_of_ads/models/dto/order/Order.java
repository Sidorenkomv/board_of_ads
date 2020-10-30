package com.board_of_ads.models.dto.order;


import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posting_id", referencedColumnName = "id")
    private Posting posting;

    @Column
    private String deliveryService;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column
    private boolean checked = false;

    public Order(User user, Posting posting, String deliveryService, DeliveryStatus status){
        this.user = user;
        this.posting = posting;
        this.deliveryService = deliveryService;
        this.status = status;
    }
}

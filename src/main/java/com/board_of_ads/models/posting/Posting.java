package com.board_of_ads.models.posting;

import com.board_of_ads.models.*;
import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.dto.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


/**
 * Суперкласс для объявлений
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "postings")
public class Posting {

    public Posting(User user, Category category, String title, String description, Long price, String contact) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.isActive = true;
    }

    public Posting(User user, Category category, String title, String description, Long price, String contact, City city) {
        this(user, category, title, description, price, contact);
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("postings")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Long price;

    @Column
    private String contact;

    @Column
    private String meetingAddress;

    @Column
    private Boolean isActive;

    @Column
    private LocalDateTime datePosting = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
}

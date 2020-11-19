package com.board_of_ads.models.posting;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.Message;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.order.Order;
import com.board_of_ads.models.dto.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("postings")
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
    private Integer viewNumber = 0;

    @Column
    private LocalDateTime datePosting = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "posting_images",
            joinColumns = @JoinColumn(name = "posting_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    public Posting(User user, Category category, String title, String description, Long price, String contact, Boolean isActive) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.isActive = isActive;
    }

    public Posting(User user, Category category, String title, String description, Long price, String contact, City city, Boolean isActive, Integer viewNumber) {
        this(user, category, title, description, price, contact, isActive);
        this.city = city;
        this.viewNumber = viewNumber;
    }


}

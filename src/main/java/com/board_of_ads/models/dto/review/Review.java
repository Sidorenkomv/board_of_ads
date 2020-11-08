package com.board_of_ads.models.dto.review;

import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Posting posting;

    @Column
    private String comment;

    @Min(1)
    @Max(5)
    @Column
    private Byte rating;

    @Column
    private boolean disabled = false;

    @Column
    private String moderatorComment = "";

    @Column
    private LocalDateTime dateTime = LocalDateTime.now();

    public Review(User user, Posting posting, Byte rating, String comment) {
        this.user = user;
        this.posting = posting;
        this.rating = rating;
        this.comment = comment;
    }
}

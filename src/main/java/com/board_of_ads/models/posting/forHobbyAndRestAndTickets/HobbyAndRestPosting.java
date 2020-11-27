package com.board_of_ads.models.posting.forHobbyAndRestAndTickets;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_hobby_and_rest")
public class HobbyAndRestPosting extends Posting {
    @Column
    private String contactEmail;

    @Column
    private String linkYouTube;

    @Column
    private String communicationType;

    @Column
    private String state;

    public HobbyAndRestPosting(User user, Category category, String title, String description, Long price, String contact, Boolean isActive,
                               String contactEmail, String linkYouTube, String communicationType, String state) {
        super(user, category, title, description, price, contact, isActive);
        this.contactEmail = contactEmail;
        this.linkYouTube = linkYouTube;
        this.communicationType = communicationType;
        this.state = state;
    }
}

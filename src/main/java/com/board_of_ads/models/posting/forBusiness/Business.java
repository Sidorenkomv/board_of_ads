package com.board_of_ads.models.posting.forBusiness;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_business")
public class Business extends Posting {

    private String state;
    private String linkYouTube;
    private String contactEmail;
    private String communicationType;

    public Business(User user, Category category, String title, String description, Long price, String contact, Boolean isActive,
                    String contactEmail, String linkYouTube, String communicationType, String state) {
        super(user, category, title, description, price, contact, isActive);

        this.state = state;

        this.linkYouTube = linkYouTube;
        this.contactEmail = contactEmail;
        this.linkYouTube = linkYouTube;
        this.communicationType = communicationType;
    }
}

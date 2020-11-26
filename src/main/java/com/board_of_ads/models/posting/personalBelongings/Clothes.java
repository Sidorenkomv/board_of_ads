package com.board_of_ads.models.posting.personalBelongings;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.City;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_clothes")
public class Clothes extends Posting {

    private String size;
    private String state;
    private String typeAd;
    private String linkYouTube;
    private String contactEmail;
    private String communicationType;

    public Clothes(User user, Category category, String title, String description, Long price, String contact, Boolean isActive,
                   String contactEmail, String linkYouTube, String communicationType, String state, String typeAd, String size) {
        super(user, category, title, description, price, contact, isActive);
        this.size = size;
        this.state = state;
        this.typeAd = typeAd;
        this.linkYouTube = linkYouTube;
        this.contactEmail = contactEmail;
        this.linkYouTube = linkYouTube;
        this.communicationType = communicationType;
    }
}
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


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Clothes extends Posting {

    private String size;
    private String state;
    private String typeAd;
    private String linkYouTube;




}

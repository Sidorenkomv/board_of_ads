package com.board_of_ads.models.posting.forHomeAndGarden;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_household_appliances")
public class HouseholdAppliancesPosting extends Posting {
    @Column
    private String contactEmail;

    @Column
    private String linkYouTube;

    @Column
    private String communicationType;

    @Column
    private String state;
}

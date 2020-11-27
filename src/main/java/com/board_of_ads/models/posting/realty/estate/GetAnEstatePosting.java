package com.board_of_ads.models.posting.realty.estate;

import com.board_of_ads.models.posting.Posting;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_get_an_estate")
public class GetAnEstatePosting extends Posting {

    @Column
    private String rooms;

    @Column
    private String contactEmail;

    @Column
    private String communicationType;

    @Column
    private String numberOfBeds;

    @Column
    private String sleeper;

    @Column
    private boolean wifi;

    @Column
    private boolean tv;

    @Column
    private boolean cable;

    @Column
    private boolean cooker;

    @Column
    private boolean microwave;

    @Column
    private boolean fridge;

    @Column
    private boolean washingMachine;

    @Column
    private boolean hairdryer;

    @Column
    private boolean flatiron;

    @Column
    private boolean nurslings;

    @Column
    private boolean children;

    @Column
    private boolean events;

    @Column
    private boolean smoke;
}

package com.board_of_ads.models.posting.realty.estate;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_buy_estate")
public class BuyEstatePosting extends Posting {

    @Column
    private String rooms;

    @Column
    private String contactEmail;

    @Column
    private String communicationType;

    public BuyEstatePosting(User user, Category category, String title, String description,
                            Long price, String contact, Boolean isActive, String rooms,
                            String contactEmail, String communicationType) {
        super(user, category, title, description, price, contact, isActive);
        this.rooms = rooms;
        this.contactEmail = contactEmail;
        this.communicationType = communicationType;
    }
}

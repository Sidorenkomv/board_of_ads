package com.board_of_ads.models.posting.job;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "posting_job")
public class JobPosting extends Posting {

    @Column
    private String schedule;

    @Column
    private String experienceValue;

    @Column
    private String placeOfWork;

    public JobPosting (User user, Category category, String title, String description, Long price, String contact, Boolean isActive,
                    String schedule, String experienceValue, String placeOfWork) {
        super(user, category, title, description, price, contact, isActive);
        this.schedule = schedule;
        this.experienceValue = experienceValue;
        this.placeOfWork = placeOfWork;
    }
}
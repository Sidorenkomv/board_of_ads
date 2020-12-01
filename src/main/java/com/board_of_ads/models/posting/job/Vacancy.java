package com.board_of_ads.models.posting.job;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posting_job_vacancies")
public class Vacancy extends JobPosting {
    @Column
    private String contactEmail;

    @Column
    private String communicationType;

    @Column
    private String frequency;

    @Column
    private String duties;

    @Column
    private Boolean isFor45;

    @Column
    private Boolean isFor14;

    @Column
    private Boolean isForHandicapped;

    public Vacancy (User user, Category category, String title, String description, Long price, String contact, Boolean isActive,
                    String schedule, String experienceValue, String placeOfWork,
                    String contactEmail, String communicationType, String frequency, String duties,
                    Boolean isFor45, Boolean isFor14, Boolean isForHandicapped) {
        super(user, category, title, description, price, contact, isActive, schedule, experienceValue, placeOfWork);
        this.contactEmail = contactEmail;
        this.communicationType = communicationType;
        this.frequency = frequency;
        this.duties = duties;
        this.isFor45 = isFor45;
        this.isFor14 = isFor14;
        this.isForHandicapped = isForHandicapped;
    }
}
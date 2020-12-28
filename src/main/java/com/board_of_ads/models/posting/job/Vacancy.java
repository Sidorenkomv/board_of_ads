package com.board_of_ads.models.posting.job;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
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
@Table(name = "posting_job_vacancies")
public class Vacancy extends JobPosting {
    @Column
    private String paymentsFrequency;

    @Column
    private String duties;

    @Column
    private String location;

    @Column
    private Boolean isFor45;

    @Column
    private Boolean isFor14;

    @Column
    private Boolean isForHandicapped;
}
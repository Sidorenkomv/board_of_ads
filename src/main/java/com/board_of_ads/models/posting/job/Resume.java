package com.board_of_ads.models.posting.job;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "posting_job_resumes")
public class Resume extends JobPosting {
    @Column
    private String education;

    @Column
    private String gender;

    @Column
    private Byte age;

    @Column
    private String readyForBusinessTrip;

    @Column
    private String relocation;

    @Column
    private String citizenship;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "posting_job_j_resumes_experiences",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Experience> experiences;


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "posting_job_j_resumes_graduatedfroms",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<GraduatedFrom> graduatedFroms;


    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "posting_job_j_resumes_levellanguages",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<LevelLanguage> levelLanguages;
}
package com.board_of_ads.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="drafts")
public class Draft {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany
    private List <Image> images;

    @OneToOne
    private User user;

    public Draft (List <Image> images, User user) {
        this.images = images;
        this.user = user;
    }
}

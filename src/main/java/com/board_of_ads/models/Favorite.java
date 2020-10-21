package com.board_of_ads.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite {

    @Id
    private Long posting;

    @Column
    private String userid;

    @Column
    private String ip;

}

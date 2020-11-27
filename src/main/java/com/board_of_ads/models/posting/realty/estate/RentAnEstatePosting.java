package com.board_of_ads.models.posting.realty.estate;

import com.board_of_ads.models.posting.Posting;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_rent_estate")
public class RentAnEstatePosting extends Posting {

    //тип жилья(вторичка/новостройка)
    @Column
    private String typeOfHousing;

    //право собственности
    @Column
    private String ownership;

    //статус кв/апартоменты
    @Column
    private String status;

    //количество комнат
    @Column
    private String rooms;

    // тип дома(дерево/монолит и др)
    @Column
    private String typeOfHouse;

    //этаж
    @Column
    private Integer floor;

    //Этажей в доме
    @Column
    private Integer floorsInHouse;

    //Общая площадь
    @Column
    private Integer totalArea;

    //Площадь кухни
    @Column
    private Integer kitchenArea;

    //Жилая площадь
    @Column
    private Integer livingArea;

    //балкон
    @Column
    private String loggia;

    //вид
    @Column
    private String view;

    @Column
    private String contactEmail;

    @Column
    private String linkYouTube;

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

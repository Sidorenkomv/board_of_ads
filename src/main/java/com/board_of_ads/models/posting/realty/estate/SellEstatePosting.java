package com.board_of_ads.models.posting.realty.estate;


import com.board_of_ads.models.*;
import com.board_of_ads.models.posting.Posting;
import lombok.*;

import javax.persistence.*;

//Пост с недвижимостью

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "posting_sell_estate")
public class SellEstatePosting extends Posting {

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

    public SellEstatePosting(User user, Category category,
                             String title, String description, Long price,
                             String contact, Boolean isActive, String typeOfHousing, String ownership,
                             String status, String rooms, String typeOfHouse,
                             Integer floor, Integer floorsInHouse,
                             Integer totalArea, Integer kitchenArea,
                             Integer livingArea, String loggia, String view,
                             String contactEmail, String linkYouTube, String communicationType) {
        super(user, category, title, description, price, contact, isActive);
        this.typeOfHousing = typeOfHousing;
        this.ownership = ownership;
        this.status = status;
        this.rooms = rooms;
        this.typeOfHouse = typeOfHouse;
        this.floor = floor;
        this.floorsInHouse = floorsInHouse;
        this.totalArea = totalArea;
        this.kitchenArea = kitchenArea;
        this.livingArea = livingArea;
        this.loggia = loggia;
        this.view = view;
        this.contactEmail = contactEmail;
        this.linkYouTube = linkYouTube;
        this.communicationType = communicationType;
    }

    //    //Количество комнат
//    @Column
//    private Integer countRoom;
//    //Является ли собственником
//    @Column
//    private boolean isProprietor;
}

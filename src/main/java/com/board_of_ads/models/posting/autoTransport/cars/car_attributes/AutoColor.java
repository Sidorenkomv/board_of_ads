package com.board_of_ads.models.posting.autoTransport.cars.car_attributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"users"})
@Table(name="auto_colors")
public class AutoColor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Size(max=10)
    private String colorHexCode;
    @Size(max=20)
    private String colorName;
    @Size(max=20)
    private String colorRusName;

    public AutoColor(String colorName, String colorRusName){
        this.colorName = colorName;
        this.colorRusName = colorRusName;
        colorHexCode = "#FFFFFF"; //white
    }
    public AutoColor(String colorName, String colorRusName, String colorHexCode) {
        this.colorName = colorName;
        this.colorRusName = colorRusName;
        this.colorHexCode = colorHexCode;
    }

}


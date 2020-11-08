package com.board_of_ads.models.dto;

import lombok.Data;

@Data
public class CategoryDtoMenu {
    private Long id;
    private String name;
    private String frontName;
    private int layer;

    public CategoryDtoMenu(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDtoMenu(Long id, String name, String frontName, int layer) {
        this.id = id;
        this.name = name;
        this.frontName = frontName;
        this.layer = layer;
    }
}

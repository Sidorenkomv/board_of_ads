package com.board_of_ads.models.dto;

import lombok.Data;

@Data
public class CategoryDtoMenu {
    private Long id;
    private String name;
    private String frontName;

    public CategoryDtoMenu(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDtoMenu(Long id, String name, String frontName) {
        this.id = id;
        this.name = name;
        this.frontName = frontName;
    }
}

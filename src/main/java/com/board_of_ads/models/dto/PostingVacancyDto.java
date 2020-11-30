package com.board_of_ads.models.dto;


import com.board_of_ads.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostingVacancyDto {
    private CategoryDto category;
    private String schedule;
    private String frequency;
    private String placeOfWork;
    private String experienceValue;
    private Boolean fits45;
    private Boolean fits14;
    private Boolean fitsHandicapped;
    private String meetingAddress;
    private String duties;
    private String description;
    private Long id;
    private String title;
    private Long price;
    private String contact;
    private String contactEmail;
    private LocalDateTime datePosting;
    private List<Image> images;
    private String city;
    private Boolean isActive;
    private Integer viewNumber;
    private String communicationType;

}

package com.board_of_ads.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportCityPostingDto {
    private String city;
    private Long postsCount;
    private Long activePostsCount;
    private Long archivePostsCount;
}

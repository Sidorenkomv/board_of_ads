package com.board_of_ads.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportCategoryPostingDto {
    private String category;
    private Long postsCount;
    private Long activePostsCount;
    private Long archivePostsCount;
}

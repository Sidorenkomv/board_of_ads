package com.board_of_ads.models.dto.analytics;

import com.board_of_ads.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportCategoryPostingDto {
    private String category;
    private Long categoryId;
    private Category parentCategory;
    private Long postsCount;
    private Long activePostsCount;
    private Long archivePostsCount;

    public ReportCategoryPostingDto(String category, Long postsCount, Long activePostsCount, Long archivePostsCount) {
        this.category = category;
        this.postsCount = postsCount;
        this.activePostsCount = activePostsCount;
        this.archivePostsCount = archivePostsCount;
    }
}

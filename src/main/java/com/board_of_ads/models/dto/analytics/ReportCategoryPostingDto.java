package com.board_of_ads.models.dto.analytics;

import com.board_of_ads.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

@Data
@AllArgsConstructor
public class ReportCategoryPostingDto implements Serializable {
    private String category;
    private int layer;
    private Category parentCategory;
    private Long postsCount;
    private Long activePostsCount;
    private Long archivePostsCount;

    public ReportCategoryPostingDto(String category, int layer, Long postsCount, Long activePostsCount, Long archivePostsCount) {
        this.category = category;
        this.layer = layer;
        this.postsCount = postsCount;
        this.activePostsCount = activePostsCount;
        this.archivePostsCount = archivePostsCount;
    }

    public static final Comparator<ReportCategoryPostingDto> COMPARE_BY_LAYER = new Comparator<ReportCategoryPostingDto>() {
        @Override
        public int compare(ReportCategoryPostingDto obj1, ReportCategoryPostingDto obj2) {
            return obj1.getLayer() - obj2.getLayer();
        }
    };
}

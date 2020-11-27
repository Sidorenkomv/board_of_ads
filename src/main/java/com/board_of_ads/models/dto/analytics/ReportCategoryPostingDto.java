package com.board_of_ads.models.dto.analytics;

import com.board_of_ads.models.Category;
import com.board_of_ads.service.interfaces.Combine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportCategoryPostingDto implements Combine<ReportCategoryPostingDto> {
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


    @Override
    public ReportCategoryPostingDto mergeParent(ReportCategoryPostingDto o1, ReportCategoryPostingDto o2) {

        if (o1.getParentCategory().getName().equals(o2.getParentCategory().getName())) {
            return new ReportCategoryPostingDto(o1.getParentCategory().getName(), o1.getPostsCount() + o2.getPostsCount(),
                    o1.getActivePostsCount() + o2.getActivePostsCount(),
                    o1.getArchivePostsCount() + o2.getArchivePostsCount());
        }
        return null;
    }

    public ReportCategoryPostingDto mergeGrandParent(ReportCategoryPostingDto obj) {

        if (this.getParentCategory().getCategory().getName().equals(obj.getParentCategory().getCategory().getName())) {
            return new ReportCategoryPostingDto(this.getParentCategory().getCategory().getName(), this.getPostsCount() + obj.getPostsCount(),
                    this.getActivePostsCount() + obj.getActivePostsCount(),
                    this.getArchivePostsCount() + obj.getArchivePostsCount());
        }
        return null;
    }
}

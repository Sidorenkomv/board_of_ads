package com.board_of_ads.models.dto.analytics;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportRegionPostingDto {
    private String region;
    private Long posts;
}

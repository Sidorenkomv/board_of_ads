package com.board_of_ads.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportUserPostingDto {

    private String userEmail;
    private Long allUserPosts;
    private Long activeUserPosts;
    private Long archiveUserPosts;

}

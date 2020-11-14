package com.board_of_ads.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageDto {
    private Long id;
    private String messageText;
    private String postingAuthorFirstName;
    private String postingAuthorLastName;
    private String postingTitle;
    private Long postingPrice;
}
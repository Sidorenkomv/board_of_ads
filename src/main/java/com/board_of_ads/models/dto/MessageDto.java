package com.board_of_ads.models.dto;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import lombok.Data;

@Data
public class MessageDto {
    Long id;
    String text;
    String author;
    Posting posting;

    public MessageDto(Long id, String text, User author, Posting posting) {
        this.id = id;
        this.text = text;
        this.author = author.getUsername();
        this.posting = posting;
    }
}

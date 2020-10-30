package com.board_of_ads.models.dto;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.service.interfaces.PostingService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostingDto {
    private PostingService postingService;
    private Long id;
    private String title;
    private String description;
    private Long price;
    private String contact;
    private LocalDateTime datePosting;
    private List<Image> images;
    private String category;
    private String city;
    private String meetingAddress;

    public PostingDto(Long id, String title, String description, Long price, String contact, LocalDateTime datePosting,String meetingAddress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.datePosting = datePosting;
        this.meetingAddress = meetingAddress;
    }

    public PostingDto(Posting posting){
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.description = posting.getDescription();
        this.price = posting.getPrice();
        this.contact = posting.getContact();
        this.datePosting = posting.getDatePosting();
        this.meetingAddress = posting.getMeetingAddress();
    }
}

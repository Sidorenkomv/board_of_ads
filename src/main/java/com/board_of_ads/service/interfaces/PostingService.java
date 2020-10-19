package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostingService {

    void save(Posting posting);

    Posting getPostingById(Long id);

    Optional<Posting> getPostingByTitle(String title);

    PostingDto getPostingDtoById(Long id);

    List<PostingDto> getPostingByCity(City city);

    List<PostingDto> getPostingByFullRegionName(String name);

    List<PostingDto> getAllPostings();
}

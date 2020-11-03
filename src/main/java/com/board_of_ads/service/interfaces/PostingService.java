package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.City;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PostingService {

    void save(Posting posting);

    Posting getPostingById(Long id);

    Optional<Posting> getPostingByTitle(String title);

    PostingDto getPostingDtoById(Long id);

    List<PostingDto> getPostingByCity(City city);

    List<PostingDto> getPostingByFullRegionName(String name);

    List<PostingDto> getAllPostings();

    List<PostingDto> getAllUserPostings(Long user_id);

    List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption);

    List<Map> getPostBetweenDates(String date);

    List<PostingDto> getFavDtosFromUser(User user);
}

package com.board_of_ads.repository;

import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    Posting findPostingByTitle(String title);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name) from Posting p where p.id = :id")
    PostingDto getPostingDtoById(@Param("id") Long id);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name) from Posting p where p.city = :city")
    List<PostingDto> findPostingByCity(@Param("city") City city);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name) from Posting p ")
    List<PostingDto> findAllPostings();

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.isActive) from Posting p where p.user.id = :user_id")
    List<PostingDto> findAllUserPostings(@Param("user_id") Long id);
}

package com.board_of_ads.repository;

import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    Posting findTopPostingByTitle(String title);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.viewNumber) from Posting p where p.id = :id")
    PostingDto getPostingDtoById(@Param("id") Long id);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.viewNumber) from Posting p where p.city = :city")
    List<PostingDto> findPostingByCity(@Param("city") City city);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.viewNumber) from Posting p ")
    List<PostingDto> findAllPostings();     //Абсолюбно бесполезный метод

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.isActive, p.viewNumber) from Posting p where p.user.id = :user_id")
    List<PostingDto> findAllUserPostings(@Param("user_id") Long id);

    @Query("select new com.board_of_ads.models.dto.analytics.ReportUserPostingDto(p.user.email, count (p.user.email), sum(case when p.isActive = true then 1 else 0 end), sum(case when p.isActive = true then 0 else 1 end)) from Posting p where p.datePosting BETWEEN :startDate and :endDate GROUP BY p.user.email")
    List<ReportUserPostingDto> findAllByDatePostingBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("update Posting p set p.viewNumber = p.viewNumber + 1 where p.id= :id")
    void addViewNumber(@Param("id") Long id);

    @Query("select new com.board_of_ads.models.dto.PostingDto(p.id, p.title, p.description, p.price, p.contact, p.datePosting,p.city.name, p.isActive, p.viewNumber) from Posting p where p.id in :favlist")
    List<PostingDto> findUserFavorites(List<Long> favlist);
}

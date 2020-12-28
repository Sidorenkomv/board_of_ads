package com.board_of_ads.repository;

import com.board_of_ads.models.ForeignLanguage;
import com.board_of_ads.models.dto.LanguageDto;
import com.board_of_ads.models.posting.job.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<ForeignLanguage, Long> {

    @Query("select new com.board_of_ads.models.dto.LanguageDto(l.language) from ForeignLanguage l ")
    List<LanguageDto> findAllLanguages();

}

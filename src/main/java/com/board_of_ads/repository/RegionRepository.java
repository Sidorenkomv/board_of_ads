package com.board_of_ads.repository;

import com.board_of_ads.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findRegionByRegionNumber(String regionNumber);

    boolean existsRegionByName(String name);

    Region findRegionByName(String name);

    @Query("select new map (r.name, count (r.name)) from Region r, Posting p where p.city.region = r AND p.datePosting BETWEEN :startDate and :endDate GROUP BY r.name")
    List<Map> findAllByDatePostingBetween(LocalDateTime startDate, LocalDateTime endDate);
}
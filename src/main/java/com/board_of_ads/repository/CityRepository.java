package com.board_of_ads.repository;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;
import com.board_of_ads.models.dto.analytics.ReportRegionPostingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface CityRepository extends JpaRepository<City, Long> {

    Set<City> findCitiesByRegion(Region region);

    boolean existsCityByNameAndRegion(String cityName, Region region);

    Optional<City> findCitiesByName(String name);

    Optional<City> findCityById(Long id);

    @Query("SELECT  u from City u where u.region.id=?1  order by u.name")
    List<City> getCitiesByRegionId(Long regionId);


    @Query("SELECT u FROM City u WHERE u.millionCity = true order by u.name")
    Set<City> getCitiesByPopulation();

    @Query("select new com.board_of_ads.models.dto.analytics.ReportCityPostingDto(" +
            "c.name, count (c.name), sum (case when p.isActive = true then 1 else 0 end), sum (case when p.isActive = true then 0 else 1 end)" +
            ")" +
            " from City c, Posting p where p.city = c AND p.datePosting BETWEEN :startDate and :endDate GROUP BY c.name")
    List<ReportCityPostingDto> findAllByDatePostingBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT c.name FROM Cities c  \n" +
            "WHERE c.name=?1 \n" +
            "LIKE '%' || c.name || '%'",
            nativeQuery = true)
    Optional<City> findCityByNameContainName(String name);
}

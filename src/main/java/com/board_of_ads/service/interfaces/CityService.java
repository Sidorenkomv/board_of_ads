package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CityService {

    Set<City> getCitiesList();

    Optional<City> findCityByName(String name);

    Optional<City> findCityById(Long id);

    List<ReportCityPostingDto> getNumberOfPostings(String date);
    List<City> getCitiesListByRegionId(Long regionId);

    Optional<City> findCityByNameContainName(String name);
}
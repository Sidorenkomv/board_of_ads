package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final RegionService regionService;

    @Override
    public Set<City> getCitiesList() {
        Set<City> cities = new HashSet<>();
        cityRepository.findAll()
                .forEach(city -> {
                    String regionName = "";
                    String formSubject = "";
                    if (city.getRegion() != null) {
                        regionName = city.getRegion().getName() == null ? "" : city.getRegion().getName();
                        formSubject = city.getRegion().getFormSubject() == null ? "" : city.getRegion().getFormSubject();
                    }
                    cities.add(new City(city.getName(), new Region(), regionName + " " + formSubject, false));
                });
        regionService.findAll()
                .forEach(region -> {
                    String regionName = region.getName();
                    String formSubject = region.getFormSubject();
                    if (formSubject.equals("Республика") || formSubject.equals("Город")) {
                        cities.add(new City(formSubject + " " + regionName, new Region(), "", false));
                    } else {
                        cities.add(new City(regionName + " " + formSubject, new Region(), "", false));
                    }
                });
        return cities;
    }

    @Override
    public Optional<City> findCityByName(String name) {
        return cityRepository.findCitiesByName(name);
    }

    @Override
    public Optional<City> findCityById(Long id) {
        return Optional.of(cityRepository.getOne(id));
    }

    @Override
    public List<ReportCityPostingDto> getNumberOfPostings(String date) {
        List<LocalDateTime> localDateTimes = dateConvertation(date);
        return cityRepository.findAllByDatePostingBetween(localDateTimes.get(0), localDateTimes.get(1));
    }

    @Override
    public Optional<City> findCityByNameContainName(String name) {
        return cityRepository.findCityByNameContainName(name);
    }

    private List<LocalDateTime> dateConvertation(String date) {

        String[] arr = date.split("\\D+");

        List<Integer> dateValues = Arrays.stream(arr)
                .filter(a -> !a.equals(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        LocalDateTime startDateTime = LocalDateTime.of(dateValues.get(2), dateValues.get(1), dateValues.get(0), 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(dateValues.get(5), dateValues.get(4), dateValues.get(3), 23, 59);

        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        localDateTimeList.add(startDateTime);
        localDateTimeList.add(endDateTime);

        return localDateTimeList;
    }
}

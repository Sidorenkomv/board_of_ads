package com.board_of_ads.service.impl;


import com.board_of_ads.models.Region;
import com.board_of_ads.models.dto.analytics.ReportRegionPostingDto;
import com.board_of_ads.repository.RegionRepository;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Optional<Region> findRegionByNameAndFormSubject(String name) {
        return regionRepository.findAll()
                .stream()
                .map(Region::getName)
                .filter(region -> (name.split(" ")[0].equals(region) || name.split(" ")[1].equals(region)))
                .map(regionRepository::findRegionByName).findFirst();
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    @Override
    public List<ReportRegionPostingDto> getNumberOfPostings(String date){
        List<LocalDateTime> localDateTimes = dateConvertation(date);
        return regionRepository.findAllByDatePostingBetween(localDateTimes.get(0), localDateTimes.get(1));
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
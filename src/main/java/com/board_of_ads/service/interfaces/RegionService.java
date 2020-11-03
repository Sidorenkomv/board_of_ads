package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Region;
import com.board_of_ads.models.dto.analytics.ReportRegionPostingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RegionService {

    Optional<Region> findRegionByNameAndFormSubject(String name);

    List<Region> findAll();

    List<ReportRegionPostingDto> getNumberOfPostings(String date);
}

package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/city")
@AllArgsConstructor
@Slf4j
public class CityRestController {

    private final CityService cityService;

    @GetMapping()
    public Response<Set<City>> findAll() {
        log.info("Use this default logger");
        var cities = cityService.getCitiesList();
        return  (cities.size() > 0)
                ? Response.ok(cities)
                : new ErrorResponse<>(new Error(204, "No found cities"));
    }

    @PostMapping("/date")
    public Response<List<ReportCityPostingDto>> findByDate(@RequestBody String date) {
        return Response.ok(cityService.getNumberOfPostings(date));
    }
}

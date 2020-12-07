package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.City;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.analytics.ReportCityPostingDto;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.UserRepository;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/city")
@AllArgsConstructor
@Slf4j
public class CityRestController {

    private final CityService cityService;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    @GetMapping()
    public Response<Set<City>> findAll() {
        log.info("Use this default logger");
        var cities = cityService.getCitiesList();
        return  (cities.size() > 0)
                ? Response.ok(cities)
                : new ErrorResponse<>(new Error(204, "No found cities"));
    }

    @GetMapping("/millionCities")
    public Response<Set<City>> findAllMillionCities() {
        Set<City> cities = cityRepository.getCitiesByPopulation();
        return (cities.size() > 0)
                ? Response.ok(cities)
                : new ErrorResponse<>(new Error(204, "No found cities"));
    }

    @GetMapping("/userCity")
    public Response<Optional <City>> getUserCity () {
        User us = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long usId = us.getId();
        Long idc = userRepository.findCityByUser(usId);
        Optional<City> city = cityRepository.findCityById(idc);
        log.info("Use this default logger");
        var cities = cityService.getCitiesList();
        return  (cities.size() > 0)
                ? Response.ok(city)
                : new ErrorResponse<>(new Error(204, "No found cities"));
    }

    @PostMapping("/date")
    public Response<List<ReportCityPostingDto>> findByDate(@RequestBody String date) {
        return Response.ok(cityService.getNumberOfPostings(date));
    }
}

package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.service.interfaces.AutoAttributesService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posting")
@AllArgsConstructor
@Slf4j
public class PostingRestController {

    private final CityService cityService;
    private final PostingService postingService;
    @Autowired
    private final AutoAttributesService autoAttributesService;

    @GetMapping
    public Response<List<PostingDto>> findAllPosts() {
        log.info("Use this default logger");
        var postings = postingService.getAllPostings();
        return (postings.size() > 0)
                ? Response.ok(postings)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }
    @GetMapping("/{id}")
    public Response<PostingDto> findPostingDto(@PathVariable Long id) {
        var postingDto = postingService.getPostingDtoById(id);
        return (postingDto != null)
                ? Response.ok(postingDto)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }

    @GetMapping("/city/{name}")
    public Response<List<PostingDto>> findPostingsByCityName(@PathVariable String name) {
        var postings = postingService.getPostingByCity(cityService.findCityByName(name).get());
        return Response.ok(postings);
    }

    @GetMapping("/region/{name}")
    public Response<List<PostingDto>> findPostingsByRegionName(@PathVariable String name) {
        var postings = postingService.getPostingByFullRegionName(name);
        return Response.ok(postings);
    }

    @GetMapping("/userpost/{id}")
    public Response<List<PostingDto>> findPostingsByUserId(@PathVariable Long id) {
        var postings = postingService.getAllUserPostings(id);
        return (postings.size() > 0)
                ? Response.ok(postings)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }

    @GetMapping("/search")
    public Response<List<PostingDto>> findAllPostings(@RequestParam(name="catSel") String categorySelect,
                                                      @RequestParam(name="citSel",required = false)String citySelect,
                                                      @RequestParam(name="searchT",required = false) String searchText,
                                                      @RequestParam(name="phOpt",required = false) String photoOption) {
        log.info("Use this default logger");
        var postings = postingService

                .searchPostings(categorySelect, citySelect, searchText, photoOption);
        return (postings != null)
                ? Response.ok(postings)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }

    @PostMapping("/date")
    public Response<List<ReportUserPostingDto>> findByDate(@RequestBody String date) {
        return Response.ok(postingService.getPostBetweenDates(date));
    }

    @PostMapping("/new")
    public Response<Void> createPosting(@RequestBody Posting posting) {
        //postingService.save(posting);
        return Response.ok().build();
    }

    @GetMapping("/car/{isCarNew}")
    public Response<PostingCarDto> getPostingCarDtoMap(@AuthenticationPrincipal User user, @PathVariable String isCarNew) {
        log.info("In Get PostingCarDTO Controller");
        PostingCarDto postingCarDto = postingService.getNewPostingCarDto(user.getId(), isCarNew);
        return  Response.ok(postingCarDto);
    }

    @GetMapping("/car/colors")
    public Response<Set<String>> getCarColorsSet() {
        log.info("In Get Set of Colors Controller");
        return  Response.ok(autoAttributesService.getAllAutoColorsRusNames());
    }

    @GetMapping("/car/brands")
    public Response<Set<String>> getCarBrandsSet() {
        log.info("In Get Set of getCarBrands Controller");
        return  Response.ok(autoAttributesService.getBrandsSet());
    }

    @GetMapping("/car/models/{brand}")
    public Response<Set<String>> getCarBrandsSet(@PathVariable String brand) {
        log.info("In Get Set of getCar Models Controller brand = {}", brand);
        return  Response.ok(autoAttributesService.getModelsSet(brand));
    }

    @GetMapping("/car/models/{brand}/{model}")
    public Response<Set<Short>> getYearsByBrandAndModel(@PathVariable String brand, @PathVariable String model) {
        log.info("In Get Set of getCar Models Controller brand = {} model = {}", brand, model);
        return  Response.ok(autoAttributesService.getYearsByBrandAndModel(brand, model));
    }

    @PostMapping("/car/new-save")
    public Response<Void> createUsedCarPostingDto(@AuthenticationPrincipal User user, @RequestBody JSONObject json) {
        log.info("In POST createUsedCarPosting Controller");
        try {
            PostingCar postingCar = postingService.convertJsonToPostingCar(json);
            postingCar.setUser(user);
            postingCar.setSellerId(user.getId());
            postingService.save(postingCar);
            log.info("Posting  Saved!");
            return Response.ok().build();
        } catch (Exception e){
            log.info("Unable to save Posting : " + e.getMessage());
            return new ErrorResponse<>(new Error(204, "Error of saving post"));
        }
    }

}
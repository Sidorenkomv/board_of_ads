package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.service.interfaces.AutoAttributesService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posting")
@AllArgsConstructor
@Slf4j
public class PostingCarRestController {
    @Autowired
    private final PostingService postingService;
    @Autowired
    private final AutoAttributesService autoAttributesService;

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

    @PostMapping("/car/new-saveA")
    public Response<Void> createUsedCarPostingDtoA(@RequestBody JSONObject json) throws JSONException {
        log.info("In POST createUsedCarPosting Controller");
        String color = json.getString("carColor");
        String type = json.getString("typeOfUsedCarPosting");
        String carB = json.getString("carBrand");
        String carM = json.getString("carModel");
        String vin = json.getString("vinCode");
//        String carIsNew = json.getString("carIsNew");
//        String carPrice = json.getString("price");
//        String plate = json.getString("statePlateNumber");
     //   System.out.println("carColor: " + postingCar.getCarColor());
    //   System.out.println("type: " + postingCar.getTypeOfUsedCarPosting());
//       System.out.println("Brand : " + carB);
//      System.out.println("Model : " + carM );
//       System.out.println("vin : " + vin);
//        System.out.println("plate : " + plate);
//       System.out.println("carIsNew : " + carIsNew);
//        System.out.println("carPrice : " + carPrice);
//
//        ObjectMapper mapper = new ObjectMapper();
//        TypeReference<PostingCarDto> typeReference = new TypeReference<PostingCarDto>(){};

//        try {
//            PostingCarDto user = mapper.readValue(json, typeReference);
//            postingService.save(user);
//            System.out.println("Users Saved!");
//        } catch (IOException e){
//            System.out.println("Unable to save users: " + e.getMessage());
//        }


       // postingService.save(postingCar);
        return Response.ok().build();
    }

    @PostMapping("/car/new-save")
    public Response<Void> createUsedCarPostingDto(@RequestBody PostingCar postingCar) throws JSONException {
        log.info("In POST createUsedCarPosting Controller");
//        String color = json.getString("carColor");
//        String type = json.getString("typeOfUsedCarPosting");
//        String carB = json.getString("carBrand");
//        String carM = json.getString("carModel");
//        String vin = json.getString("vinCode");
//        String carIsNew = json.getString("carIsNew");
//        String carPrice = json.getString("price");
//        String plate = json.getString("statePlateNumber");
        System.out.println("carColor: " + postingCar.getCarColor());
        System.out.println("type: " + postingCar.getTypeOfUsedCarPosting());
//       System.out.println("Brand : " + carB);
//      System.out.println("Model : " + carM );
//       System.out.println("vin : " + vin);
//        System.out.println("plate : " + plate);
//       System.out.println("carIsNew : " + carIsNew);
//        System.out.println("carPrice : " + carPrice);



//        try {
//            PostingCarDto user = mapper.readValue(json, typeReference);
//            postingService.save(user);
//            System.out.println("Users Saved!");
//        } catch (IOException e){
//            System.out.println("Unable to save users: " + e.getMessage());
//        }


        // postingService.save(postingCar);
        return Response.ok().build();
    }

}

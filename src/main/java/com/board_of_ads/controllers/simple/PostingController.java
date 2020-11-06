package com.board_of_ads.controllers.simple;

import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.service.interfaces.AutoAttributesService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
@AllArgsConstructor
@Slf4j
public class PostingController {
    @Autowired
    AutoAttributesService autoAttributesService;
    @Autowired
    PostingService postingService;
    @Autowired
    UserService userService;

    @GetMapping("/posting-used-car")
    public String addNewPost(@AuthenticationPrincipal User user, Model model) {
        log.info("In PostingController. GET method...");
        if (user == null) { user = new User();}
        PostingCar carPostObject = new PostingCar();
        System.out.println("User id = " + user.getId());
        carPostObject.setSellerId(user.getId());
        System.out.println("Seller id = " + carPostObject.getSellerId());
        carPostObject.setViewNumber(1);
        Set<String> brandsSet = autoAttributesService.getBrandsSet();
        Set<String> typeOfUsedCarPostingSet = autoAttributesService.getTypeOfUsedCarPostingSet();
        Set<String> autoColorsRusNames = autoAttributesService.getAllAutoColorsRusNames();
        Set<String> powerSteeringTypes = autoAttributesService.getPowerSteeringTypes();
        Set<String> climateControlTypes = autoAttributesService.getClimateControlTypes();
        Set<String> interiorTypes = autoAttributesService.getAllInteriorTypes();
        Set<String> powerWindowTypes = autoAttributesService.getPowerWindowTypes();
        Set<String> audioSystemTypes = autoAttributesService.getAudioSystemTypes();
        Set<String> frontLightTypes = autoAttributesService.getFrontLightTypes();
        model.addAttribute("user", user);
        model.addAttribute("seller_id", user.getId());
        model.addAttribute("carPostObject", carPostObject);
        model.addAttribute("brandsSet", brandsSet);
        model.addAttribute("typeOfUsedCarPostingSet", typeOfUsedCarPostingSet);
        model.addAttribute("autoColorsRusNames", autoColorsRusNames);
        model.addAttribute("powerSteeringTypes", powerSteeringTypes);
        model.addAttribute("climateControlTypes", climateControlTypes);
        model.addAttribute("interiorTypes", interiorTypes);
        model.addAttribute("powerWindowTypes", powerWindowTypes);
        model.addAttribute("audioSystemTypes", audioSystemTypes);
        model.addAttribute("frontLightTypes", frontLightTypes);

        return "postings/posting-used-car";
    }

    @PostMapping("/save-used-car-posting")
    public String saveNewUedCarPost(@ModelAttribute("carPostObject") PostingCar carPostObject) {
        log.info("In Post method");
        carPostObject.setIsActive(true);

        System.out.println(carPostObject.getSellerId());
        System.out.println(carPostObject.getPrice());
        System.out.println("--");
//        User seller = userService.getUserById(carPostObject.getSellerId());
//        carPostObject.setUser(seller);
       // carPostObject.setDatePosting(LocalDateTime.now());
        showPostingAllFields(carPostObject);

        postingService.saveNewPostingCar(carPostObject);
        return "postings/save-result";
    }

    private void showPostingAllFields(PostingCar ucp){
        System.out.println("Result:");
        System.out.println("typeOfUsedCarPosting = " + ucp.getTypeOfUsedCarPosting());
      //  System.out.println("autoPhotoLink = " + ucp.getAutoPhotoLink());

        System.out.println("-- brand  = " + ucp.getCarBrand());
        System.out.println("-- Car year  = " + ucp.getCarYear());

        System.out.println("color = " + ucp.getCarColor());
        System.out.println("videoLink = " + ucp.getVideoURL());
        System.out.println("vin = " + ucp.getVinCode());

        System.out.println("mileage = " + ucp.getMileage());
        System.out.println("wasInAccident = " + ucp.isWasInAccident());

        System.out.println("numberOfOwners = " + ucp.getNumberOfOwners());
        System.out.println("hasServiceBook = " + ucp.isHasServiceBook());
        System.out.println("dealerServiced = " + ucp.isDealerServiced());
        System.out.println("warrantyValid = " + ucp.isUnderWarranty());

        System.out.println("leatherWheel = " + ucp.isLeatherWheel());
        System.out.println("sunroof = " + ucp.isSunroof());


        System.out.println(" car price = " + ucp.getPrice());

        System.out.println("parkingAssist = " + ucp.isParkingAssist());
        System.out.println("rainSensor = " + ucp.isRainSensor());
        System.out.println("lightSensor = " + ucp.isLightSensor());
        System.out.println("rearParkingSensor = " + ucp.isRearParkingSensor());



    }

}

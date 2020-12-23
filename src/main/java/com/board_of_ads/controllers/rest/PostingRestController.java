package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.models.posting.forAudioVideo.AudioVideoPosting;
import com.board_of_ads.models.posting.forHobbyAndRestAndTickets.HobbyAndRestPosting;
import com.board_of_ads.models.posting.forHobbyAndRestAndTickets.TicketsPosting;
import com.board_of_ads.models.posting.forDogs.DogBreed;
import com.board_of_ads.models.posting.forDogs.dogsPosting;
import com.board_of_ads.models.posting.forHomeAndGarden.HouseholdAppliancesPosting;
import com.board_of_ads.models.posting.job.Vacancy;
import com.board_of_ads.models.posting.realty.estate.BuyEstatePosting;
import com.board_of_ads.models.posting.realty.estate.GetAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.RentAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.SellEstatePosting;
import com.board_of_ads.service.interfaces.AutoAttributesService;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.DogBreedService;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private final CategoryService categoryService;
    private final UserService userService;
    private final ImageService imageService;
    private final DogBreedService dogBreedService;

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
    public Response<List<PostingDto>> findAllPostings(@RequestParam(name = "catSel") String categorySelect,
                                                      @RequestParam(name = "citSel", required = false) String citySelect,
                                                      @RequestParam(name = "searchT", required = false) String searchText,
                                                      @RequestParam(name = "phOpt", required = false) String photoOption) {
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
        return Response.ok(postingCarDto);
    }

    @GetMapping("/car/colors")
    public Response<Set<String>> getCarColorsSet() {
        log.info("In Get Set of Colors Controller");
        return Response.ok(autoAttributesService.getAllAutoColorsRusNames());
    }

    @GetMapping("/car/brands")
    public Response<Set<String>> getCarBrandsSet() {
        log.info("In Get Set of getCarBrands Controller");
        return Response.ok(autoAttributesService.getBrandsSet());
    }

    @GetMapping("/car/models/{brand}")
    public Response<Set<String>> getCarBrandsSet(@PathVariable String brand) {
        log.info("In Get Set of getCar Models Controller brand = {}", brand);
        return Response.ok(autoAttributesService.getModelsSet(brand));
    }

    @GetMapping("/car/models/{brand}/{model}")
    public Response<Set<Short>> getYearsByBrandAndModel(@PathVariable String brand, @PathVariable String model) {
        log.info("In Get Set of getCar Models Controller brand = {} model = {}", brand, model);
        return Response.ok(autoAttributesService.getYearsByBrandAndModel(brand, model));
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
        } catch (Exception e) {
            log.info("Unable to save Posting : " + e.getMessage());
            return new ErrorResponse<>(new Error(204, "Error of saving post"));
        }
    }


    @PostMapping("/new/householdAppliances/{id}")
    public Response<Void> createHouseholdAppliancesPosting(@PathVariable Long id,
                                                           @AuthenticationPrincipal User user,
                                                           @RequestParam Map<String, String> obj,
                                                           @RequestParam(value = "photos") List<MultipartFile> photos) {
        HouseholdAppliancesPosting posting;

        try {
            posting = new HouseholdAppliancesPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("state"));

            List<Image> images = new ArrayList<>();
            String time = new SimpleDateFormat("yyyy'-'MM'-'dd'_'HHmmss'_'").format(new Date());
            try {
                for (int i = 0; i < photos.size(); i++) {
                    if (!photos.get(i).isEmpty()) {
                        byte[] bytes = photos.get(i).getBytes();
                        File dir = new File("uploaded_files/userID_" + user.getId());
                        dir.mkdirs();
                        File file = new File(dir, time + photos.get(i).getOriginalFilename());
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));

                        stream.write(bytes);
                        stream.close();
                        Image image = new Image(dir.toString() + file.toString());
                        imageService.save(image);
                        images.add(imageService.getByPathURL(dir.toString() + file.toString()));
                        log.info("Файл '" + time + photos.get(i).getOriginalFilename() + "' успешно загружен.");
                    } else {
                        log.info("Вам не удалось загрузить файл, потому что он пустой.");
                    }
                }
            } catch (Exception ex) {
                log.info("Вам не удалось загрузить фотографии => " + ex.getMessage());
                return new ErrorResponse<>(new Error(400, "Posting is not created"));
            }
            posting.setImages(images);
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/sellEstate/{id}")
    public Response<Void> createSellEstatePosting(@PathVariable Long id,
                                                  @AuthenticationPrincipal User user,
                                                  @RequestParam Map<String,String> obj,
                                                  @RequestParam(value = "photos") List<MultipartFile> photos) {

        try {
            SellEstatePosting posting = postingService.addSellEstatePosting(obj);
            List<Image> images =  imageService.savePhotos(user, photos);
            posting.setUser(user);
            posting.setCategory(categoryService.getCategoryById(id).getCategory());
            posting.setCity(user.getCity());
            posting.setImages(images);
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }


    @PostMapping("/new/buyEstate/{id}")
    public Response<Void> createBuyEstatePosting(@PathVariable Long id,
                                                 @AuthenticationPrincipal User user,
                                                 @RequestParam Map<String,String> obj) {

        try {
            BuyEstatePosting posting = postingService.addBuyEstatePosting(obj);
            posting.setUser(user);
            posting.setCategory(categoryService.getCategoryById(id).getCategory());
            posting.setCity(user.getCity());
            postingService.save(posting);

            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }


    @PostMapping("/new/rentAnEstate/{id}")
    public Response<Void> rentAnEstatePosting(@PathVariable Long id,
                                              @AuthenticationPrincipal User user,
                                              @RequestParam Map<String,String> obj,
                                              @RequestParam(value = "photos") List<MultipartFile> photos) {
        try {
            RentAnEstatePosting posting = postingService.addRentAnEstatePosting(obj);
            List<Image> images = imageService.savePhotos(user, photos);

            posting.setUser(user);
            posting.setCategory(categoryService.getCategoryById(id).getCategory());
            posting.setCity(user.getCity());
            posting.setImages(images);
            postingService.save(posting);

            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();

        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }

    }

    @PostMapping("/new/getAnEstate/{id}")
    public Response<Void> getAnEstatePosting(@PathVariable Long id,
                                             @AuthenticationPrincipal User user,
                                             @RequestParam Map<String,String> obj,
                                             @RequestParam(value = "photos") List<MultipartFile> photos) {
        try {
            GetAnEstatePosting posting = postingService.addGetAnEstatePosting(obj);
            List<Image> images = imageService.savePhotos(user, photos);

            posting.setUser(user);
            posting.setCategory(categoryService.getCategoryById(id).getCategory());
            posting.setCity(user.getCity());
            posting.setImages(images);
            postingService.save(posting);

            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();

        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/vacancy/{id}")
    public Response<Void> createVacancyPosting(@PathVariable Long id,
                                               @AuthenticationPrincipal User user,
                                               @RequestParam Map<String,String> obj,
                                               @RequestParam(value = "photos") List<MultipartFile> photos) {
        Vacancy posting;

        try {
            posting = new Vacancy(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("schedule"), obj.get("experienceValue"), obj.get("placeOfWork"),
                    obj.get("contactEmail"), obj.get("communicationType"), obj.get("frequency"), obj.get("duties"),
                    Boolean.parseBoolean(obj.get("isFor45")), Boolean.parseBoolean(obj.get("isFor14")),
                    Boolean.parseBoolean(obj.get("isForHandicapped")));
            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Одинцово").get());
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/audiovideo/{id}")
    public Response<Void> createAudioVideoPosting(@PathVariable Long id,
                                                  @AuthenticationPrincipal User user,
                                                  @RequestParam Map<String, String> obj,
                                                  @RequestParam(value = "photos") List<MultipartFile> photos) {
        AudioVideoPosting posting;

        try {
            posting = new AudioVideoPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("state"));
            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/hobbyAndRest/{id}")
    public Response<Void> createHobbyAndRestPosting(@PathVariable Long id,
                                                    @AuthenticationPrincipal User user,
                                                    @RequestParam Map<String, String> obj,
                                                    @RequestParam(value = "photos") List<MultipartFile> photos) {
        HobbyAndRestPosting posting;

        try {
            posting = new HobbyAndRestPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("state"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/dogs/{id}")
    public Response<Void> createDogsPosting(@PathVariable Long id,
                                            @AuthenticationPrincipal User user,
                                            @RequestParam Map<String, String> obj,
                                            @RequestParam(value = "photos") List<MultipartFile> photos) {
        dogsPosting posting;

        try {
            posting = new dogsPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("breed"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @PostMapping("/new/tickets/{id}")
    public Response<Void> createTicketsPosting(@PathVariable Long id,
                                               @AuthenticationPrincipal User user,
                                               @RequestParam Map<String, String> obj,
                                               @RequestParam(value = "photos") List<MultipartFile> photos) {
        TicketsPosting posting;

        try {
            posting = new TicketsPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"));
            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingService.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @GetMapping("/getdogbreeds")
    public Response<List<DogBreed>> getDogBreeds() {
        log.info("getDogsBreed Controller");
        return Response.ok(dogBreedService.findAll());
    }

    @PostMapping("/clothes/{id}")
    public Response<Void> createPersonalClothesPosting(@PathVariable Long id,
                                                       @AuthenticationPrincipal User user,
                                                       @RequestParam Map<String, String> map,
                                                       @RequestParam(value = "photos") List<MultipartFile> photos) {

        log.info("Create posting clothes");
        return postingService.savePersonalClothesPosting(id, user, map, photos);
    }


    @PostMapping("/business/{id}")
    public Response<Void> createForBusinessPosting(@PathVariable Long id,
                                                   @AuthenticationPrincipal User user,
                                                   @RequestParam Map<String, String> map,
                                                   @RequestParam(value = "photos") List<MultipartFile> photos) {

        log.info("Create posting for business");
        return postingService.saveForBusinessPosting(id, user, map, photos);
    }
}

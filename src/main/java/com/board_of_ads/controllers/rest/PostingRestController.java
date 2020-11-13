package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.forHomeAndGarden.HouseholdAppliancesPosting;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@RequestMapping("/api/posting")
@AllArgsConstructor
@Slf4j
public class PostingRestController {

    private final CityService cityService;
    private final PostingService postingService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ImageService imageService;

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

    @PostMapping("/new/householdAppliances/{id}")
    public Response<Void> createHouseholdAppliancesPosting(@PathVariable Long id,
                                                           @AuthenticationPrincipal User user,
                                                           @RequestParam Map<String,String> obj,
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
}
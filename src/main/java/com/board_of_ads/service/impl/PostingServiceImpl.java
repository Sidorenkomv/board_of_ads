package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.personalBelongings.Clothes;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.PostingRepository;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RegionService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostingServiceImpl implements PostingService {

    private final PostingRepository postingRepository;
    private final CategoryService categoryService;
    private final RegionService regionService;
    private final CityRepository cityRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    public void save(Posting posting) {
        postingRepository.save(posting);
    }

    @Override
    public Posting getPostingById(Long id) {
        return postingRepository.getOne(id);
    }

    @Override
    public Optional<Posting> getPostingByTitle(String title) {
        return Optional.ofNullable(postingRepository.findPostingByTitle(title));
    }

    @Override
    public PostingDto getPostingDtoById(Long id) {
        postingRepository.addViewNumber(id);
        PostingDto postingDto = postingRepository.getPostingDtoById(id);
        postingDto.setImages(getPostingById(postingDto.getId()).getImages());
        postingDto.setCategory(categoryService.getCategoryDtoById(
                getPostingById(postingDto.getId()).getCategory().getId()).get());
        if (getPostingById(postingDto.getId()).getCity() != null) {
            postingDto.setCity(getPostingById(postingDto.getId()).getCity().getName());
        }
        return postingDto;
    }

    @Override
    public List<PostingDto> getPostingByCity(City city) {
        List<PostingDto> result = postingRepository.findPostingByCity(city);
        return getPostingDtos(result);
    }

    @Override
    public List<PostingDto> getPostingByFullRegionName(String name) {
        List<PostingDto> result = new ArrayList<>();
        var cities = cityRepository.findCitiesByRegion(
                regionService.findRegionByNameAndFormSubject(name).get());
        cities.forEach(city -> result.addAll(postingRepository.findPostingByCity(city)));
        return getPostingDtos(result);
    }

    @Override
    public List<PostingDto> getAllPostings() {
        List<PostingDto> postingDtos = postingRepository.findAllPostings();
        return getPostingDtos(postingDtos);
    }

    @Override
    public List<PostingDto> getAllUserPostings(Long user_id) {
        List<PostingDto> userPosts = postingRepository.findAllUserPostings(user_id);
        return getPostingDtos(userPosts);
    }

    private List<PostingDto> getPostingDtos(List<PostingDto> postingDtos) {
        for (PostingDto dto : postingDtos) {
            dto.setImages(getPostingById(dto.getId()).getImages());
            dto.setCategory(categoryService.getCategoryDtoById(
                    postingRepository.findPostingByTitle(dto.getTitle()).getCategory().getId()).get());
            if (getPostingById(dto.getId()).getCity() != null) {
                dto.setCity(getPostingById(dto.getId()).getCity().getName());
            }
        }
        return postingDtos;
    }

    @Override
    public List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption) {

        List<PostingDto> postingDtos;
        if (citySelect != null && !(citySelect.equals("undefined"))) {
            if (citySelect.matches("(.*)" + "Область" + "(.*)")
                    || citySelect.matches("(.*)" + "Край" + "(.*)")
                    || citySelect.matches("(.*)" + "Республика" + "(.*)")
                    || citySelect.matches("(.*)" + "Автономный округ" + "(.*)")
                    || citySelect.matches("(.*)" + "Город" + "(.*)")
            ) {
                postingDtos = getPostingByFullRegionName(citySelect);
            } else {
                postingDtos = getPostingByCity(cityRepository.findCitiesByName(citySelect).get());
            }
        } else {
            postingDtos = getAllPostings();
        }

        List<PostingDto> resultList = new ArrayList<>();

        for (PostingDto postingDto : postingDtos) {

            boolean categoryFlag = false;
            boolean photoFlag = false;
            boolean textFlag = false;

            if (categorySelect.equals("Любая категория")) {
                categoryFlag = true;
            } else if (postingDto.getCategory().equals(categorySelect)) {
                categoryFlag = true;
            }
            if (photoOption != null) {
                if (photoOption.equals("пункт2")) {
                    if (postingDto.getImages().size() > 0) {
                        photoFlag = true;
                    }
                } else if (photoOption.equals("пункт3")) {
                    if (postingDto.getImages().size() == 0) {
                        photoFlag = true;
                    }
                } else if (photoOption.equals("пункт1")) {
                    photoFlag = true;
                }
            } else {
                photoFlag = true;
            }
            if (searchText != null && !(searchText.equals("")) && !(searchText.equals("undefined"))) {
                if (postingDto.getTitle().toLowerCase().matches("(.*)" + searchText.toLowerCase() + "(.*)")) {
                    textFlag = true;
                }
            } else {
                textFlag = true;
            }

            if (categoryFlag && photoFlag && textFlag) {
                resultList.add(postingDto);
            }
        }

        return resultList;
    }

    @Override
    public List<ReportUserPostingDto> getPostBetweenDates(String date) {
        List<LocalDateTime> localDateTimes = dateConvertation(date);
        return postingRepository.findAllByDatePostingBetween(localDateTimes.get(0), localDateTimes.get(1));
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

    public List<PostingDto> getFavDtosFromUser(User user) {
        List<Long> listfavoritsid = new ArrayList<>();
        user.getFavorites().forEach(x -> listfavoritsid.add(x.getId()));
        return postingRepository.findUserFavorites(listfavoritsid);
    }

    public List<Long> getFavIDFromUser(User user) {
        List<Long> listfavoritsid = new ArrayList<>();
        user.getFavorites().forEach(x -> listfavoritsid.add(x.getId()));
        return listfavoritsid;
    }

    public Response<Void> savePersonalClothesPosting(Long id, User user, Map<String,
            String> map, List<MultipartFile> photos) {

        Clothes posting;
        try {

            posting = new Clothes(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    map.get("title"), map.get("description"), Long.parseLong(map.get("price")), map.get("contact"),
                    true, map.get("contactEmail"), map.get("linkYouTube"), map.get("communicationType"), map.get("state"), map.get("typeAd"), map.get("size"));

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
            postingRepository.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }


}
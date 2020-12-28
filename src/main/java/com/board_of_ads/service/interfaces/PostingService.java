package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.models.posting.job.Resume;
import com.board_of_ads.models.posting.job.Vacancy;
import com.board_of_ads.models.posting.realty.estate.BuyEstatePosting;
import com.board_of_ads.models.posting.realty.estate.GetAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.RentAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.SellEstatePosting;
import com.board_of_ads.util.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostingService {

    void save(Posting posting);

    Posting getPostingById(Long id);

    Optional<Posting> getPostingByTitle(String title);

    PostingDto getPostingDtoById(Long id);

    List<PostingDto> getPostingByCity(City city);

    List<PostingDto> getPostingByFullRegionName(String name);

    List<PostingDto> getAllPostings();

    List<PostingDto> getAllUserPostings(Long user_id);

    List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption);

    List<ReportUserPostingDto> getPostBetweenDates(String date);

    List<PostingDto> getFavDtosFromUser(User user);

    List<Long> getFavIDFromUser(User user);

    PostingCarDto getNewPostingCarDto(Long userId, String isCarNew);

    PostingCar convertJsonToPostingCar(JSONObject json) throws JSONException;

    BuyEstatePosting addBuyEstatePosting(Map<String,String> obj) throws Exception;

    GetAnEstatePosting addGetAnEstatePosting(Map<String,String> obj) throws Exception;

    RentAnEstatePosting addRentAnEstatePosting(Map<String,String> obj) throws Exception;

    SellEstatePosting addSellEstatePosting(Map<String,String> obj) throws Exception;

    Response<Void> savePersonalClothesPosting(Long id, User user, Map<String,
            String> map, List<MultipartFile> photos);

    Response<Void> saveForBusinessPosting(Long id, User user, Map<String,
            String> map, List<MultipartFile> photos);

    Response<Void> saveHouseholdAppliancesPosting(Long id, User user, Map<String,String> map,
                                                  List<MultipartFile> photos);
}

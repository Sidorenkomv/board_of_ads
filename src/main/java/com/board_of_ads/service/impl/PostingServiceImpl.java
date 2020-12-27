package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.dto.analytics.ReportUserPostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import com.board_of_ads.models.posting.forBusiness.Business;
import com.board_of_ads.models.posting.forCats.catsPosting;
import com.board_of_ads.models.posting.forHomeAndGarden.HouseholdAppliancesPosting;
import com.board_of_ads.models.posting.job.Vacancy;
import com.board_of_ads.models.posting.personalBelongings.Clothes;
import com.board_of_ads.models.posting.realty.estate.BuyEstatePosting;
import com.board_of_ads.models.posting.realty.estate.GetAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.RentAnEstatePosting;
import com.board_of_ads.models.posting.realty.estate.SellEstatePosting;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.PostingCarRepository;
import com.board_of_ads.repository.PostingRepository;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RegionService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostingServiceImpl implements PostingService {

    private final UserService userService;
    private final PostingRepository postingRepository;
    private final CategoryService categoryService;
    private final RegionService regionService;
    private final CityRepository cityRepository;
    private final PostingCarRepository postingCarRepository;
    private final CityService cityService;
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
        return Optional.ofNullable(postingRepository.findTopPostingByTitle(title));
    }

    @Override
    public PostingDto getPostingDtoById(Long id) {
        postingRepository.addViewNumber(id);
        PostingDto postingDto = postingRepository.getPostingDtoById(id);
        postingDto.setImages(getPostingById(postingDto.getId()).getImages());
        postingDto.setCategory(categoryService.getCategoryDtoById(
                getPostingById(postingDto.getId()).getCategory().getId()).get());
        if(getPostingById(postingDto.getId()).getCity() != null) {
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
        for(PostingDto dto : postingDtos) {
            dto.setImages(getPostingById(dto.getId()).getImages());
            dto.setCategory(categoryService.getCategoryDtoById(
                    postingRepository.findTopPostingByTitle(dto.getTitle()).getCategory().getId()).get());
            if(getPostingById(dto.getId()).getCity() != null) {
                dto.setCity(getPostingById(dto.getId()).getCity().getName());
            }
        }
        return postingDtos;
    }

    @Override
    public List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption) {

        List<PostingDto> postingDtos;
        if(citySelect != null && !(citySelect.equals("undefined"))) {
            if (citySelect.matches("(.*)" +"Область" + "(.*)")
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
            if(photoOption != null) {
                if(photoOption.equals("пункт2")) {
                    if(postingDto.getImages().size() > 0) {
                        photoFlag = true;
                    }
                } else if(photoOption.equals("пункт3")) {
                    if(postingDto.getImages().size() == 0) {
                        photoFlag = true;
                    }
                } else if(photoOption.equals("пункт1")) {
                    photoFlag = true;
                }
            } else {
                photoFlag = true;
            }
            if(searchText != null && !(searchText.equals("")) && !(searchText.equals("undefined"))) {
                if(postingDto.getTitle().toLowerCase().matches("(.*)" + searchText.toLowerCase() + "(.*)")) {
                    textFlag = true;
                }
            } else {
                textFlag = true;
            }

            if(categoryFlag && photoFlag && textFlag) {
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

    @Override
    public List<PostingDto> getFavDtosFromUser(User user) {
        List<Long> listfavoritsid = new ArrayList<>();
        user.getFavorites().forEach(x ->listfavoritsid.add(x.getId()));
        return postingRepository.findUserFavorites(listfavoritsid);
    }

    @Override
    public List<Long> getFavIDFromUser(User user) {
        List<Long> listfavoritsid = new ArrayList<>();
        user.getFavorites().forEach(x ->listfavoritsid.add(x.getId()));
        return listfavoritsid;
    }

    @Override
    public PostingCarDto getNewPostingCarDto(Long userId, String isCarNew) {
        User user = userService.getUserById(userId);
        PostingCar pc = new PostingCar();
        pc.setUser(user);
        pc.setSellerId(userId);
        pc.setCarIsNew(!isCarNew.equals("used-car"));

        return new PostingCarDto(pc);
    }

    @Override
    public PostingCar convertJsonToPostingCar(JSONObject json) throws JSONException {
        PostingCar pc = new PostingCar();

        pc.setVinCode(json.getString("vinCode"));
        pc.setCarIsNew(json.getBoolean("carIsNew"));
        pc.setTypeOfUsedCarPosting(json.getString("typeOfUsedCarPosting"));
        pc.setStatePlateNumber(json.getString("statePlateNumber"));
        pc.setMileage(json.getInt("mileage"));
        pc.setNumberOfOwners((byte) json.getInt("numberOfOwners"));
        pc.setModelIdInAutoCatalogue(0); //
        pc.setCarColor(json.getString("carColor"));
        pc.setCarBrand(json.getString("carBrand"));
        pc.setCarModel(json.getString("carModel"));
        pc.setCarYear((short) json.getInt("carYear"));
        pc.setCarBodyType(json.getString("carBodyType"));
        pc.setNumberOfDoors((byte) json.getInt("numberOfDoors"));
        pc.setWasInAccident(json.getBoolean("wasInAccident"));
        pc.setDealerServiced(json.getBoolean("dealerServiced"));
        pc.setUnderWarranty(json.getBoolean("underWarranty"));
        pc.setHasServiceBook(json.getBoolean("hasServiceBook"));
        pc.setPowerSteeringType(json.getString("powerSteeringType"));
        pc.setClimateControlType(json.getString("climateControlType"));
        pc.setClimateControlType(json.getString("climateControlType"));
        pc.setOnWheelControl(json.getBoolean("onWheelControl"));
        pc.setThermalGlass(json.getBoolean("thermalGlass"));
        pc.setInteriorType(json.getString("interiorType"));
        pc.setLeatherWheel(json.getBoolean("leatherWheel"));
        pc.setSunroof(json.getBoolean("sunroof"));
        pc.setHeatedFrontSeats(json.getBoolean("heatedFrontSeats"));
        pc.setHeatedRearSeats(json.getBoolean("heatedRearSeats"));
        pc.setHeatedMirrors(json.getBoolean("heatedMirrors"));
        pc.setHeatedRearWindow(json.getBoolean("heatedRearWindow"));
        pc.setHeatedWheel(json.getBoolean("heatedWheel"));
        pc.setPowerWindowsType(json.getString("powerWindowsType"));
        pc.setPowerFrontSeats(json.getBoolean("powerFrontSeats"));
        pc.setPowerRearSeats(json.getBoolean("powerRearSeats"));
        pc.setPowerMirrorRegulation(json.getBoolean("powerMirrorRegulation"));
        pc.setPowerSteeringWheelRegulation(json.getBoolean("powerSteeringWheelRegulation"));
        pc.setPowerMirrorClose(json.getBoolean("powerMirrorClose"));
        pc.setFrontSeatsMemory(json.getBoolean("frontSeatsMemory"));
        pc.setRearSeatsMemory(json.getBoolean("rearSeatsMemory"));
        pc.setMirrorRegulationMemory(json.getBoolean("mirrorRegulationMemory"));
        pc.setSteeringWheelRegulationMemory(json.getBoolean("steeringWheelRegulationMemory"));
        pc.setParkingAssist(json.getBoolean("parkingAssist"));
        pc.setRainSensor(json.getBoolean("rainSensor"));
        pc.setLightSensor(json.getBoolean("lightSensor"));
        pc.setRearParkingSensor(json.getBoolean("rearParkingSensor"));
        pc.setFrontParkingSensor(json.getBoolean("frontParkingSensor"));
        pc.setBlindSpotZoneControl(json.getBoolean("blindSpotZoneControl"));
        pc.setRearCamera(json.getBoolean("rearCamera"));
        pc.setCruiseControl(json.getBoolean("cruiseControl"));
        pc.setOnBoardComp(json.getBoolean("onBoardComp"));
        pc.setAlarmSystem(json.getBoolean("alarmSystem"));
        pc.setPowerDoorBlocking(json.getBoolean("powerDoorBlocking"));
        pc.setImmobilizer(json.getBoolean("immobilizer"));
        pc.setSatelliteAlarmControl(json.getBoolean("satelliteAlarmControl"));
        pc.setFrontalAirbags(json.getBoolean("frontalAirbags"));
        pc.setKneeAirbags(json.getBoolean("kneeAirbags"));
        pc.setSideWindowAirbags(json.getBoolean("sideWindowAirbags"));
        pc.setFrontSideAirbags(json.getBoolean("frontSideAirbags"));
        pc.setRearSideAirbags(json.getBoolean("rearSideAirbags"));
        pc.setAbsSystem(json.getBoolean("absSystem"));
        pc.setDtcSystem(json.getBoolean("dtcSystem"));
        pc.setTrackingControl(json.getBoolean("trackingControl"));
        pc.setBreakAssistSystem(json.getBoolean("breakAssistSystem"));
        pc.setEmergencyBreakSystem(json.getBoolean("emergencyBreakSystem"));
        pc.setDiffLockSystem(json.getBoolean("diffLockSystem"));
        pc.setPedestrianDetectSystem(json.getBoolean("pedestrianDetectSystem"));
        pc.setCdDvdBluRay(json.getBoolean("cdDvdBluRay"));
        pc.setMp3(json.getBoolean("mp3"));
        pc.setRadio(json.getBoolean("radio"));
        pc.setTvSystem(json.getBoolean("tvSystem"));
        pc.setVideoSystem(json.getBoolean("videoSystem"));
        pc.setMediaOnWheelControl(json.getBoolean("mediaOnWheelControl"));
        pc.setUsb(json.getBoolean("usb"));
        pc.setAux(json.getBoolean("aux"));
        pc.setBluetooth(json.getBoolean("bluetooth"));
        pc.setGpsNavigation(json.getBoolean("gpsNavigation"));
        pc.setAudioSystemType(json.getString("audioSystemType"));
        pc.setSubwoofer(json.getBoolean("subwoofer"));
        pc.setFrontLightType(json.getString("frontLightType"));
        pc.setAntifogLights(json.getBoolean("antifogLights"));
        pc.setFrontLightCleaning(json.getBoolean("frontLightCleaning"));
        pc.setAdaptiveLights(json.getBoolean("adaptiveLights"));
        pc.setHowToContactVsSeller(json.getString("howToContactVsSeller"));
        pc.setTyreSize(json.getString("winterTyreSetIncluded"));
        pc.setWinterTyreSetIncluded(json.getBoolean("winterTyreSetIncluded"));
        pc.setTypeOfEngine("Hybrid");
        pc.setWheelDrive("4x4");
        pc.setTransmission("Automatic");
        pc.setModification("Lux");
        pc.setConfiguration(json.getString("configuration"));
        pc.setTitle(json.getString("title"));
        pc.setDescription(json.getString("description"));
        pc.setContact(json.getString("contact"));
        pc.setMeetingAddress(json.getString("meetingAddress"));
        pc.setDatePosting(LocalDateTime.now());
        pc.setCondition(json.getString("condition"));
        pc.setVideoURL(json.getString("videoURL"));
        pc.setContactEmail(json.getString("contactEmail"));
        //  pc.setMessage(json.getString("message"));
        pc.setPrice(json.getLong("price"));
        pc.setIsActive(json.getBoolean("isActive"));
        // pc.setViewNumber(json.getInt("viewNumber"));
        pc.setViewNumber(1);
        long catId =  json.getInt("categoryId");
        pc.setCategory(categoryService.getCategoryById(catId));
        return pc;
    }

    @Override
    public BuyEstatePosting addBuyEstatePosting(Map<String,String> obj) throws Exception {

        BuyEstatePosting posting = new BuyEstatePosting();
        posting.setTitle(obj.get("title"));
        posting.setDescription(obj.get("description"));
        posting.setContact(obj.get("contact"));
        posting.setIsActive(true);
        posting.setRooms(obj.get("rooms"));
        posting.setContactEmail(obj.get("contactEmail"));
        posting.setCommunicationType(obj.get("communicationType"));
        return posting;
    }

    @Override
    public GetAnEstatePosting addGetAnEstatePosting(Map<String,String> obj) throws Exception {

        GetAnEstatePosting posting = new GetAnEstatePosting();
        posting.setTitle(obj.get("title"));
        posting.setDescription(obj.get("description"));
        posting.setPrice(Long.parseLong(obj.get("price")));
        posting.setContact(obj.get("contact"));
        posting.setIsActive(true);
        posting.setRooms(obj.get("rooms"));
        posting.setContactEmail(obj.get("contactEmail"));
        posting.setCommunicationType(obj.get("communicationType"));
        posting.setNumberOfBeds(obj.get("numberOfBeds"));
        posting.setSleeper(obj.get("sleeper"));
        posting.setWifi(Boolean.getBoolean(obj.get("wifi")));
        posting.setTv(Boolean.getBoolean(obj.get("tv")));
        posting.setCable(Boolean.getBoolean(obj.get("cable")));
        posting.setCooker(Boolean.getBoolean(obj.get("cooker")));
        posting.setMicrowave(Boolean.getBoolean(obj.get("microwave")));
        posting.setFridge(Boolean.getBoolean(obj.get("fridge")));
        posting.setWashingMachine(Boolean.getBoolean(obj.get("washingMachine")));
        posting.setHairdryer(Boolean.getBoolean(obj.get("hairdryer")));
        posting.setFlatiron(Boolean.getBoolean(obj.get("flatiron")));
        posting.setNurslings(Boolean.getBoolean(obj.get("nurslings")));
        posting.setChildren(Boolean.getBoolean(obj.get("children")));
        posting.setEvents(Boolean.getBoolean(obj.get("events")));
        posting.setSmoke(Boolean.getBoolean(obj.get("smoke")));
        return posting;
    }

    @Override
    public RentAnEstatePosting addRentAnEstatePosting(Map<String,String> obj) throws Exception {

        RentAnEstatePosting posting = new RentAnEstatePosting();;
        posting.setTitle(obj.get("title"));
        posting.setDescription(obj.get("description"));
        posting.setPrice(Long.parseLong(obj.get("price")));
        posting.setContact(obj.get("contact"));
        posting.setIsActive(true);
        posting.setTypeOfHousing(obj.get("typeOfHousing"));
        posting.setOwnership(obj.get("ownership"));
        posting.setStatus(obj.get("status"));
        posting.setRooms(obj.get("rooms"));
        posting.setTypeOfHouse(obj.get("typeOfHouse"));
        posting.setFloor(Integer.parseInt(obj.get("floor")));
        posting.setFloorsInHouse(Integer.parseInt(obj.get("floorsInHouse")));
        posting.setTotalArea(Integer.parseInt(obj.get("totalArea")));
        posting.setKitchenArea(Integer.parseInt(obj.get("kitchenArea")));
        posting.setLivingArea(Integer.parseInt(obj.get("livingArea")));
        posting.setLoggia(obj.get("loggia"));
        posting.setView(obj.get("view"));
        posting.setContactEmail(obj.get("contactEmail"));
        posting.setLinkYouTube(obj.get("linkYouTube"));
        posting.setCommunicationType(obj.get("communicationType"));
        posting.setNumberOfBeds(obj.get("numberOfBeds"));
        posting.setSleeper(obj.get("sleeper"));
        posting.setWifi(Boolean.getBoolean(obj.get("wifi")));
        posting.setTv(Boolean.getBoolean(obj.get("tv")));
        posting.setCable(Boolean.getBoolean(obj.get("cable")));
        posting.setCooker(Boolean.getBoolean(obj.get("cooker")));
        posting.setMicrowave(Boolean.getBoolean(obj.get("microwave")));
        posting.setFridge(Boolean.getBoolean(obj.get("fridge")));
        posting.setWashingMachine(Boolean.getBoolean(obj.get("washingMachine")));
        posting.setHairdryer(Boolean.getBoolean(obj.get("hairdryer")));
        posting.setFlatiron(Boolean.getBoolean(obj.get("flatiron")));
        posting.setNurslings(Boolean.getBoolean(obj.get("nurslings")));
        posting.setChildren(Boolean.getBoolean(obj.get("children")));
        posting.setEvents(Boolean.getBoolean(obj.get("events")));
        posting.setSmoke(Boolean.getBoolean(obj.get("smoke")));
        return posting;
    }

    @Override
    public SellEstatePosting addSellEstatePosting(Map<String,String> obj) throws Exception {

        SellEstatePosting posting = new SellEstatePosting();
        posting.setTitle(obj.get("title"));
        posting.setDescription(obj.get("description"));
        posting.setPrice(Long.parseLong(obj.get("price")));
        posting.setContact(obj.get("contact"));
        posting.setIsActive(true);
        posting.setTypeOfHousing(obj.get("typeOfHousing"));
        posting.setOwnership(obj.get("ownership"));
        posting.setStatus(obj.get("status"));
        posting.setRooms(obj.get("rooms"));
        posting.setTypeOfHouse(obj.get("typeOfHouse"));
        posting.setFloor(Integer.parseInt(obj.get("floor")));
        posting.setFloorsInHouse(Integer.parseInt(obj.get("floorsInHouse")));
        posting.setTotalArea(Integer.parseInt(obj.get("totalArea")));
        posting.setKitchenArea(Integer.parseInt(obj.get("kitchenArea")));
        posting.setLivingArea(Integer.parseInt(obj.get("livingArea")));
        posting.setLoggia(obj.get("loggia"));
        posting.setView(obj.get("view"));
        posting.setContactEmail(obj.get("contactEmail"));
        posting.setLinkYouTube(obj.get("linkYouTube"));
        posting.setCommunicationType(obj.get("communicationType"));
        return posting;
    }

    public Response<Void> savePersonalClothesPosting(Long id, User user, Map<String,
            String> map, List<MultipartFile> photos) {

        Clothes posting;
        try {

            posting = new Clothes(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    map.get("title"), map.get("description"), Long.parseLong(map.get("price")), map.get("contact"),
                    true, map.get("contactEmail"), map.get("linkYouTube"), map.get("communicationType"), map.get("state"), map.get("typeAd"), map.get("size"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingRepository.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @Override
    public void setVacancyCondition(Map<String, String> form, List<String> preferences, User userById,
                                    Vacancy posting, City city, List<Image> images) {
        StringBuilder options = new StringBuilder();
        preferences.forEach(a -> options.append(a).append("/"));

        posting.setUser(userById);
        posting.setCategory(categoryService.getCategoryById(Long.valueOf(form.get("categoryId"))));
        posting.setCity(city);
        posting.setContact(userById.getEmail());
        posting.setDatePosting(LocalDateTime.now());
        posting.setDescription(form.get("description"));
        posting.setTitle(form.get("title"));
        posting.setIsActive(true);
        posting.setSchedule(form.get("schedule"));
        posting.setDuties(form.get("duties"));
        posting.setExperienceValue(form.get("workExperience"));
        posting.setPlaceOfWork(form.get("placeOfWork"));
        posting.setPrice(Long.valueOf(form.get("price")));
        posting.setImages(images);
    }

    @Override
    public Response<Void> saveForBusinessPosting(Long id, User user, Map<String,
            String> map, List<MultipartFile> photos) {

        Business posting;
        try {

            posting = new Business(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    map.get("title"), map.get("description"), Long.parseLong(map.get("price")), map.get("contact"),
                    true, map.get("contactEmail"), map.get("linkYouTube"), map.get("communicationType"), map.get("state"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(cityService.findCityByName("Ростов-на-Дону").get());
            postingRepository.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }

    @Override
    public Response<Void> saveHouseholdAppliancesPosting(Long id, User user, Map<String, String> obj, List<MultipartFile> photos) {

        HouseholdAppliancesPosting posting;

        try {
            posting = new HouseholdAppliancesPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("state"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(user.getCity());
            postingRepository.save(posting);
            log.info("Объявление в категории Для дома и дачи успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление в категории Для дома и дачи => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }


    }

    @Override
    public Response<Void> saveCatsPosting(Long id, User user, Map<String, String> obj, List<MultipartFile> photos) {
        catsPosting posting;

        try {
            posting = new catsPosting(userService.getUserById(user.getId()), categoryService.getCategoryById(id),
                    obj.get("title"), obj.get("description"), Long.parseLong(obj.get("price")), obj.get("contact"),
                    true, obj.get("contactEmail"), obj.get("linkYouTube"), obj.get("communicationType"), obj.get("breed"));

            List<Image> images = imageService.savePhotos(user, photos);
            posting.setImages(images);
            posting.setCity(user.getCity());
            postingRepository.save(posting);
            log.info("Объявление успешно создано пользователем " + user.getEmail());
            return Response.ok().build();
        } catch (Exception ex) {
            log.info("Не удалось создать объявление => " + ex.getMessage());
            return new ErrorResponse<>(new Error(400, "Posting is not created"));
        }
    }
}
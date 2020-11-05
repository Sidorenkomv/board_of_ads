package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoColor;
import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoModel;

import java.io.IOException;
import java.util.Set;

public interface AutoAttributesService {
    void saveNewAutoColor(AutoColor autoColor);
    Set<String> getAllAutoColorsRusNames();
    Set<String> getTypeOfUsedCarPostingSet();
    Set<String> getPowerSteeringTypes();
    Set<String> getClimateControlTypes();
    Set<String> getAllInteriorTypes();
    Set<String> getPowerWindowTypes();
    Set<String> getAudioSystemTypes();
    Set<String> getFrontLightTypes();

    void saveModel(AutoModel auto);
    void getDataFromAutoCatalogueExcel() throws IOException;
    Set<String> getBrandsSet();
}

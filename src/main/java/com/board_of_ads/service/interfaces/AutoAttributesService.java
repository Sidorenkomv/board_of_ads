package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoColor;
import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AutoAttributesService {
    void saveNewAutoColor(AutoColor autoColor);
    Set<String> getAllAutoColorsRusNames();
    Set<Short> getYearsByBrandAndModel(String brand, String model);

    void saveModel(AutoModel auto);
    void getDataFromAutoCatalogueExcel() throws IOException;
    Set<String> getBrandsSet();
    Set<String> getModelsSet(String brand);
    AutoModel getAutoModelByBrandAndModel(String brand, String model);
}

package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Country;

import java.io.IOException;
import java.util.List;

public interface CountryService {
    void saveCountry() throws IOException;
    List<Country> findAll();
}

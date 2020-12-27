package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.posting.forCats.CatBreed;

import java.io.IOException;
import java.util.List;

public interface CatBreedService {
    void saveCatBreed() throws IOException;
    List<CatBreed> findAll();
}

package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.posting.forDogs.DogBreed;

import java.io.IOException;
import java.util.List;

public interface DogBreedService {
    void saveDogBreed() throws IOException;
    List<DogBreed> findAll();
}

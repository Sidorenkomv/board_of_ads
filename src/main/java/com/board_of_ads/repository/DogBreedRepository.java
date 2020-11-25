package com.board_of_ads.repository;

import com.board_of_ads.models.posting.forDogs.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogBreedRepository extends JpaRepository<DogBreed, Long> {
    List<DogBreed> findAll();
}

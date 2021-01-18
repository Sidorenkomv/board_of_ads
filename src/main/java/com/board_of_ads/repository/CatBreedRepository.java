package com.board_of_ads.repository;

import com.board_of_ads.models.posting.forCats.CatBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatBreedRepository extends JpaRepository<CatBreed, Long> {
    List<CatBreed> findAll();
}

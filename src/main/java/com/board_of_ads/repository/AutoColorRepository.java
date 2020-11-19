package com.board_of_ads.repository;

import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoColorRepository extends JpaRepository<AutoColor, Integer> {
    // @Query("select n from AutoColor n ")
    List<AutoColor> findAll();
}

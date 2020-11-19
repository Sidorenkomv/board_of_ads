package com.board_of_ads.repository;

import com.board_of_ads.models.dto.CategoryDtoMenu;
import com.board_of_ads.models.posting.autoTransport.cars.car_attributes.AutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoModelRepository extends JpaRepository<AutoModel, Long> {
    List<AutoModel> findAll();
    List<AutoModel> findAllByBrand(String brand);
    AutoModel findAutoModelByBrandAndModel(String brand, String model);

}

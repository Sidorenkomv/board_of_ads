package com.board_of_ads.repository;

import com.board_of_ads.models.posting.autoTransport.cars.PostingCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostingCarRepository extends JpaRepository<PostingCar, Long> {
}

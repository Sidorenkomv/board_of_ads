package com.board_of_ads.repository;

import com.board_of_ads.models.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRepository extends JpaRepository <Draft, Long> {
}

package com.board_of_ads.repository;

import com.board_of_ads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void deleteById(Long id);

    @Query(
            value = "SELECT city_id FROM users where id = ?",
            nativeQuery = true)
    Long findCityByUser(Long id);

}
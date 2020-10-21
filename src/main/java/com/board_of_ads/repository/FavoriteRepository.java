package com.board_of_ads.repository;

import com.board_of_ads.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Modifying
    @Query("update Favorite f set f.userid = :userid where f.ip = :ip")
    void updateFavoriteSetUseridForIp(@Param("userid") String userid, @Param("ip") String ip);
}

package com.board_of_ads.repository;

import com.board_of_ads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void deleteById(Long id);

    @Modifying
    @Query(
            value = "delete from users_favorites userfav where userfav.user_id =?1 and userfav.posting_id =?2",
            nativeQuery = true)
    void removeFavorite(Long userID, Long postingID);

    @Modifying
    @Query(
            value = "insert into users_favorites (user_id, posting_id) values(:userid, :postingid)",
            nativeQuery = true)
    void AddFavorite(Long userid, Long postingid);
}
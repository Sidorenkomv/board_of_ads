package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite addFavorite(Favorite favorite);

    void deleteFavorite(Long id);

    void updateFavoriteSetUseridForIp(String userid, String ip);

    void updateFavoriteSetUseridForIpAfter(String ip, String userid);

    List<Favorite> findParentLikeIp(String ip);

    List<Favorite> findParentLikeId(String id);
}

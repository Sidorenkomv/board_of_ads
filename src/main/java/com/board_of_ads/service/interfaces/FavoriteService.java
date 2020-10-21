package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Favorite;

import java.util.List;

public interface FavoriteService {

    List<Favorite> findAll();

    Favorite addFavorite(Favorite favorite);

    void deleteFavorite(Long id);

    void updateFavoriteSetUseridForIp(String userid, String ip);
}

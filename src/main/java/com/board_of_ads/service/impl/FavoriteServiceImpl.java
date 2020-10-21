package com.board_of_ads.service.impl;

import com.board_of_ads.models.Favorite;
import com.board_of_ads.repository.FavoriteRepository;
import com.board_of_ads.service.interfaces.FavoriteService;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorite addFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
        return favorite;
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    @Override
    public void updateFavoriteSetUseridForIp(String userid, String ip) {
        favoriteRepository.updateFavoriteSetUseridForIp(userid, ip);
    }
}

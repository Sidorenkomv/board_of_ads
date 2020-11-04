package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.repository.ImageRepository;
import com.board_of_ads.service.interfaces.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image getPathById(Long id) {
        return imageRepository.getById(id);
    }

    @Override
    public void save(Image image) {imageRepository.save(image);}

    @Override
    public Image getByPathURL(String url) {
        return imageRepository.getByPathURL(url);
    }
}

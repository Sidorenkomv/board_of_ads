package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<Image> savePhotos(User user, List<MultipartFile> photos);

    Image getPathById(Long id);

    void save(Image image);

    Image getByPathURL(String url);

}

package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.repository.ImageRepository;
import com.board_of_ads.service.interfaces.ImageService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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

    public List<Image> savePhotos(User user, List<MultipartFile> photos){
        List<Image> images = new ArrayList<>();

        String time = new SimpleDateFormat("yyyy'-'MM'-'dd'_'HHmmss'_'").format(new Date());

        try {
            for (int i = 0; i < photos.size(); i++) {
                if (!photos.get(i).isEmpty()) {
                    byte[] bytes = photos.get(i).getBytes();
                    File dir = new File("uploaded_files/userID_" + user.getId());
                    dir.mkdirs();
                    File dirthum = new File("uploaded_files/userID_" + user.getId() + "/thumbnail/");
                    dirthum.mkdirs();

                    File file = new File(dir, time + photos.get(i).getOriginalFilename());
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                    stream.write(bytes);
                    stream.close();
                    Image image = new Image(file.getPath());
                    save(image);
                    images.add(getByPathURL(file.getPath()));
                    log.info("Файл '" + time + photos.get(i).getOriginalFilename() + "' успешно загружен.");
                } else {
                    log.info("Вам не удалось загрузить файл, потому что он пустой.");
                }
            }
        } catch (Exception ex) {
            log.info("Вам не удалось загрузить фотографии => " + ex.getMessage());
            return null;
        }
        return images;
    }
}

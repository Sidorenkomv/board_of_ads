package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Draft;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;

import java.util.List;

public interface DraftService {

    Draft saveDraft(List<Image> images, Long id);

}

package com.board_of_ads.service.impl;

import com.board_of_ads.models.Draft;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.repository.DraftRepository;
import com.board_of_ads.service.interfaces.DraftService;
import com.board_of_ads.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DraftServiceImpl implements DraftService {

    @Autowired
    DraftRepository draftRepository;
    @Autowired
    UserService userService;

    @Override
    public Draft saveDraft(List<Image> images, Long id) {
        User user = userService.getUserById(id);

        return draftRepository.save(new Draft(images, user));
    }
}

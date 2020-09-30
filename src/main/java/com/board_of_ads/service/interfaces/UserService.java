package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    User saveUser(User user);
}

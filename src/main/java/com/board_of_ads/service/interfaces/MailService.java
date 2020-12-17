package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

import java.util.Map;

public interface MailService {

    void auth(String code);

    String getAuthURL();

    String getAuthResponseURL(String code);

    String getToken(String body);

    Map<String, String> getUserData(String token, String sig);

    User init(Map<String, String> userData);

    void login(User user);
}

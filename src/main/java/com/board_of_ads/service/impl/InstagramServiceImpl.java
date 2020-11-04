package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.InstagramService;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(value = "spring.security.auth-instagram")
public class InstagramServiceImpl implements InstagramService {

    private final UserService userService;
    private final RoleService roleService;

    private String clientId;
    private String clientSecret;
    private String redirectURI;
    private String authURL;
    private String tokenURL;
    private String userInfoURL;
    private String grantType;
    private String response_type;

    public void auth(String code) {
        String requestBody = getRequestBody(code);
        String token = getToken(requestBody);
        Map<String, String> userData = getUserData(token);
        User user = init(userData);
        login(user);
    }
    /**
     * Метод для кнопки авторизации
     * @return ссылку для авторизации при переходе по которой, получим ссылку с
     *          аргументом code необходимым для метода getAuthResponse(String code).
     */
    @Override
    public String getAuthURL() {
        return authURL + "?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectURI
                + "&scope=user_profile"
                + "&response_type=code";
    }

    /**
     * @param code получаем из ссылки возвращаемой методом getAuthURL()
     * @return тело запроса, для получения access_token
     */
    @Override
    public String getRequestBody(String code) {
        return "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=authorization_code"
                + "&redirect_uri=" + redirectURI
                + "&code=" + code;
    }

    /**
     * @param body получаем из метода getRequestBody(String code)
     * @return токен авторизации пользователя Yandex Passport
     */
    @Override
    public String getToken(String body) {
        HttpHeaders head = new HttpHeaders();
        head.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        HttpEntity<String> httpEntity = new HttpEntity<>(body,head);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.exchange(tokenURL, HttpMethod.POST, httpEntity, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(entity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String token = (String) jsonObject.get("access_token");
        return token;
    }

    /**
     * @param token получаем из метода getToken(String body)
     * @return Map с instagram_id и username.
     */
    @Override
    public Map<String, String> getUserData(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = userInfoURL + "?fields=id,username&access_token=" + token;
        ResponseEntity<String> responseEntity = restTemplate.exchange(url , HttpMethod.GET, httpEntity, String.class);
        Object obj = null;
        try {
            obj = new JSONParser().parse(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        Map<String, String> userData = new HashMap<>();
        userData.put("instagram_id", (String) jsonObject.get("id"));
        userData.put("username", (String) jsonObject.get("username"));

        url = "https://api.instagram.com/" + jsonObject.get("username");
        responseEntity = restTemplate.exchange(url , HttpMethod.GET, httpEntity, String.class);
        String html = responseEntity.getBody();
        html.replaceAll("\n", "");
        String[] data = html.split("<title>")[1].split("</title>");
        String photoLink = data[1].split("<meta property=\"og:image\" content=\"")[1].split("\" />")[0];
        userData.put("photoLink", photoLink);
        System.out.println(photoLink);
        String[] fullName = data[0].split(" ");
        userData.put("firstName", fullName[0]);
        userData.put("lastName", fullName[1]);

        return userData;
    }

    /**
     * Метод для получения сессии пользователя
     */
    @Override
    public void login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Метод инициализации пользователя.
     * Если такой пользователь есть в базе данных, то он вернет его.
     * Если пользователя не существует, то он его создаст, добавит в БД и вернет.
     *
     * @param userData возвращается методом getUserData
     */
    @Override
    public User init(Map<String, String> userData) {
        User user = userService.getUserByEmail("https://instagram.com/" + userData.get("username"));
        if (user != null) {
            return user;
        }
        user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("USER"));
        user.setRoles(roles);
        user.setEnable(true);
        user.setDataRegistration(LocalDateTime.now());
        user.setAvatar(new Image(null, userData.get("photoLink")));
        user.setEmail("https://instagram.com/" + userData.get("username"));
        user.setFirstName(userData.get("firstName"));
        user.setLastName(userData.get("lastName"));
        user.setPassword(userData.get("instagram_id")); //todo create set password page (and phone)
        userService.saveUser(user);
        return user;
    }
}

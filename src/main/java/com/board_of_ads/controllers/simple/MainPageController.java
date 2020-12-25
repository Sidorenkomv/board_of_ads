package com.board_of_ads.controllers.simple;

import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class MainPageController {

    private UserService userService;

    @GetMapping("/")
    public String getMainPage(@AuthenticationPrincipal() User user, Model model, HttpSession session) {
        log.info("Use this default logger");

        if (user == null) {
            String sessionId = session.getId();
            user = userService.getUserByEmail(sessionId);
            if (user == null) {
                user = new User(sessionId);
                userService.saveUser(user);
            }

        } else {

        }
        model.addAttribute("user", user != null ? user : new User());
        return "main-page";
    }

    @GetMapping("/admin_page")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        return "admin-page";
    }

    @GetMapping("/new_post")
    public String addNewPost(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user != null ? user : new User());
        return "new-post-page";
    }

    @GetMapping("/{id}")
    public String postingPage(@AuthenticationPrincipal User user, Model model, @PathVariable Long id) {
        model.addAttribute("user", user != null ? user : new User());
        model.addAttribute("DtoId", id);
        return "posting-page";
    }

    @GetMapping("/confirm/")
    public String confirmPassword() {
        return "main-page";
    }

    @GetMapping("/favorite-not-auth")
    public String myFavoritePage(@AuthenticationPrincipal() User user, Model model) {
        model.addAttribute("user", user != null ? user : new User());
        return "favorite";
    }

    @GetMapping("/messenger")
    public String messenger(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        return "messenger";
    }
}
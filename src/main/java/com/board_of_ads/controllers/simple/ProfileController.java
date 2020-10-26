package com.board_of_ads.controllers.simple;

import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping
public class ProfileController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model) {
        addAttributesToProfile(user, model);
        return "profile";
    }

    @GetMapping("/ads")
    public String profileAdsPage(@AuthenticationPrincipal User user, Model model) {
        addAttributesToProfile(user, model);
        return "profile";
    }

    @GetMapping("/notifications")
    public String profileNotificationsPage(@AuthenticationPrincipal User user, Model model) {
        addAttributesToProfile(user, model);
        return "profile-notifications";
    }

    private void addAttributesToProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        int[] count = notificationService.getUsersNotificationsCountMap(user);
        boolean hasNew = false;
        if (count[0] > 0) { hasNew = true; }
        model.addAttribute("countMap", count);
        model.addAttribute("hasNew", hasNew);
    }


}

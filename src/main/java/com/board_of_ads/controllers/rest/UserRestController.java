package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.UserDto;
import com.board_of_ads.service.interfaces.AuthorizationService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public Response<User> getUser(@AuthenticationPrincipal User user) {
        log.info("Use this default logger");
        User us = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long usId = us.getId();
        user = userService.getUserById(usId);
        return user != null
                ? Response.ok(user)
                : Response.error().code(HttpStatus.UNAUTHORIZED).text("No auth user").build();
    }

    @PostMapping("/modal-reg")
    public Response<User> Action(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (userService.checkUserDataBeforeReg(user, bindingResult, log)) {
            authorizationService.login(user);
            return Response.ok(user);
        }
        return Response.error().code(HttpStatus.NO_CONTENT).text("Incorrect Data").build();
    }

    @PutMapping
    public Response<User> changeUserData(@AuthenticationPrincipal User principal, @RequestBody UserDto user) {
        try {
            log.info("In changeUserData get user: {}", user);
            var result = userService.update(principal, user);
            log.info("For the user with id: {} parameters has been successfully changed", principal.getId());
            return Response.ok(result);
        } catch (Exception e) {
            log.error("Parameters not changed: user: {}", user);
            return Response.error().code(HttpStatus.NOT_MODIFIED).text(e.getMessage()).build();
        }
    }
}

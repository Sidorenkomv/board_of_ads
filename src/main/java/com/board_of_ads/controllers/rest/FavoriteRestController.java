package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Favorite;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.repository.UserRepository;
import com.board_of_ads.service.interfaces.FavoriteService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.BindingResultLogs;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/favorite")
@AllArgsConstructor
public class FavoriteRestController {
    private final UserRepository userRepository;
    private final BindingResultLogs bindingResultLogs;
    private final UserService userService;
    private final PostingService postingService;


    @PostMapping(value = "/add/{id}")
    public ResponseEntity<?> addFavorite(@PathVariable("id") Long postingID,
                                         @AuthenticationPrincipal() User user,
                                         HttpSession session) {
        log.info("Favorite ADD logger " + postingID);

        if (user == null) {
            user = userService.getUserByEmail(session.getId());
        }
        Set<Posting> userFavorites = (user.getFavorites() != null) ? user.getFavorites() : new HashSet<Posting>();
        userFavorites.add(postingService.getPostingById(postingID));
        user.setFavorites(userFavorites);
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long postingID,
                                        @AuthenticationPrincipal() User user,
                                        HttpSession session) {
        log.info("Favorite delete logger " + postingID);
        if (user == null) {
            user = userService.getUserByEmail(session.getId());
        }
        userRepository.removeFavorite(user.getId(), postingID);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get")
    public Response<List<Long>> getUserFavorites(@AuthenticationPrincipal() User user, HttpSession session) {
        if (user == null) {
            user = userService.getUserByEmail(session.getId());
        }
        else {
            user = userService.getUserById(user.getId());
        }
        return Response.ok(postingService.getFavIDFromUser(user));
    }

}

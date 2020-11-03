package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Favorite;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/favorite")
@AllArgsConstructor
public class FavoriteRestController {

    private final FavoriteService favoriteService;
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
        //Set<Posting> userFavorites = user.getFavorites();
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
        Set<Posting> userFavorites = user.getFavorites();
//        System.out.println(user.getFavorites());
        userFavorites.removeIf(post -> post.getId().equals(postingID));
        user.setFavorites(userFavorites);
//        System.out.println(user.getFavorites());
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get")
    public Response<List<PostingDto>> getUserFavorites(@AuthenticationPrincipal() User user, HttpSession session) {
        if (user == null) {
            user = userService.getUserByEmail(session.getId());
        } else {
            user = userService.getUserById(user.getId());
        }
        return Response.ok(postingService.getFavDtosFromUser(user));
    }

//    @GetMapping("/userid/{id}")
//    public Response<List<Favorite>> getAllUsersListById(@PathVariable(name = "id") String id) {
//        List<Favorite> favoriteList = favoriteService.findParentLikeId(id);
//        return (favoriteList.size() > 0)
//                ? Response.ok(favoriteList)
//                : new ErrorResponse<>(new Error(204, "No favorite in table"));
//    }
//
//    @GetMapping("/userip")
//    public Response<List<Favorite>> getAllUsersListByIp() throws UnknownHostException {
//        List<Favorite> favoriteList = favoriteService.findParentLikeIp(InetAddress.getLocalHost().getHostAddress());
//        return (favoriteList.size() > 0)
//                ? Response.ok(favoriteList)
//                : new ErrorResponse<>(new Error(204, "No favorite in table"));
//    }
//
//    @PostMapping(value = "/add/none/authentication")
//    public Response<Favorite> add(@RequestBody @Valid Favorite favorite, BindingResult bindingResult) throws UnknownHostException {
//        log.info("Wish this default logger");
//
//        if (bindingResultLogs.checkUserFields(bindingResult, log)) {
//            favorite.setIp(InetAddress.getLocalHost().getHostAddress());
//            return new SuccessResponse<>(favoriteService.addFavorite(favorite));
//        }
//        return new ErrorResponse<>(new Error(204, "Incorrect Data"));
//    }
//
//    @PostMapping(value = "/add/authentication")
//    public Response<Favorite> addAfterAuthentication(@RequestBody @Valid Favorite favorite, BindingResult bindingResult) {
//        log.info("Favorite  this default logger");
//
//        if (bindingResultLogs.checkUserFields(bindingResult, log)) {
//            return new SuccessResponse<>(favoriteService.addFavorite(favorite));
//        }
//        return new ErrorResponse<>(new Error(204, "Incorrect Data"));
//    }
//
//    @GetMapping("/addregid/{id}")
//    public Response<Void> getFavoriteByIpUpdateId(@PathVariable(name = "id") String id) throws UnknownHostException {
//        favoriteService.updateFavoriteSetUseridForIp(id, InetAddress.getLocalHost().getHostAddress());
//        return new Response<>();
//    }
//
//    @GetMapping("/addregipafter/{id}")
//    public Response<Void> getFavoriteByIpUpdateIpAfter(@PathVariable(name = "id") String id) throws UnknownHostException {
//        favoriteService.updateFavoriteSetUseridForIpAfter(id, InetAddress.getLocalHost().getHostAddress());
//        return new Response<>();
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Response<Void> deleteById(@PathVariable(name = "id") Long id) {
//        favoriteService.deleteFavorite(id);
//        return new Response<>();
//    }

}

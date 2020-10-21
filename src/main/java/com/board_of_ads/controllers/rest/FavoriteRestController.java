package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Favorite;
import com.board_of_ads.service.interfaces.FavoriteService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/favorite")
@AllArgsConstructor
public class FavoriteRestController {

    private final FavoriteService favoriteService;
    private final BindingResultLogs bindingResultLogs;

    @GetMapping("/all")
    public Response<List<Favorite>> getAllUsersList() {
        List<Favorite> favoriteList = favoriteService.findAll();
        return (favoriteList.size() > 0)
                ? Response.ok(favoriteList)
                : new ErrorResponse<>(new Error(204, "No favorite in table"));
    }

    @PostMapping(value = "/add")
    public Response<Favorite> add(@RequestBody @Valid Favorite favorite, BindingResult bindingResult) throws UnknownHostException {
        log.info("Wish this default logger");

        if (bindingResultLogs.checkUserFields(bindingResult, log)) {
            favorite.setIp(InetAddress.getLocalHost().getHostAddress());
            return new SuccessResponse<>(favoriteService.addFavorite(favorite));
        }
        return new ErrorResponse<>(new Error(204, "Incorrect Data"));
    }

    @GetMapping("/addregid/{id}")
    public Response<Void> getFavoriteByIpUpdateId(@PathVariable(name = "id") String id) throws UnknownHostException {
        favoriteService.updateFavoriteSetUseridForIp(id, InetAddress.getLocalHost().getHostAddress());
        return new Response<>();
    }

    @DeleteMapping("/delete/{id}")
    public Response<Void> deleteById(@PathVariable(name = "id") Long id) {
        favoriteService.deleteFavorite(id);
        return new Response<>();
    }

}

package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posting")
@AllArgsConstructor
@Slf4j
public class PostingCarRestController {
    private final PostingService postingService;

    @GetMapping("/cars/{userId}/{isCarNew}")
    public Response<PostingCarDto> getPostingCarDtoMap(@PathVariable Long  userId, @PathVariable String isCarNew) {

        PostingCarDto postingCarDto = postingService.getNewPostingCarDto(userId, isCarNew);
        return  Response.ok(postingCarDto);

    }

}

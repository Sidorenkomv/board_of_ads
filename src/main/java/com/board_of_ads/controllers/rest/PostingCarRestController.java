package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.PostingCarDto;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/car/{isCarNew}")
    public Response<PostingCarDto> getPostingCarDtoMap(@AuthenticationPrincipal User user, @PathVariable String isCarNew) {
        log.info("In Get PostingCarDTO Controller");
        System.out.println("User Id = " + user.getId());
        PostingCarDto postingCarDto = postingService.getNewPostingCarDto(user.getId(), isCarNew);
        System.out.println(postingCarDto.getTypeOfUsedCarPosting());
        return  Response.ok(postingCarDto);
    }

}

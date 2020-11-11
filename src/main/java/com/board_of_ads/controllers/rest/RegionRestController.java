package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.dto.analytics.ReportRegionPostingDto;
import com.board_of_ads.service.interfaces.RegionService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/region/")
@Slf4j
public class RegionRestController {
    private RegionService regionService;

    @PostMapping("/date")
    public Response<List<ReportRegionPostingDto>> findByDate(@RequestBody String date) {
        var postings = regionService.getNumberOfPostings(date);
        return (postings.size() > 0)
                ? Response.ok(postings)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }
}

package com.board_of_ads.controllers.rest;


import com.board_of_ads.models.dto.MessageDto;
import com.board_of_ads.service.interfaces.MessageService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/messages")
public class MessageRestController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    public MessageRestController(){}

    @GetMapping("/{id}")
    public Response<List<MessageDto>> findAllMessages(@PathVariable Long id){
        var messages = messageService.getAllUserMessages(id);
        return (messages.size() > 0)
                ? Response.ok(messages)
                : new ErrorResponse<>(new Error(204, "No found postings"));
    }
}

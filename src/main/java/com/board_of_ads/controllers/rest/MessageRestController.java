package com.board_of_ads.controllers.rest;


import com.board_of_ads.models.dto.MessageDto;
import com.board_of_ads.service.interfaces.MessageService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/messages")
public class MessageRestController {

    private MessageService messageService;

    private UserService userService;

    public MessageRestController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Response<List<MessageDto>> findAllMessages(@PathVariable Long id){
        var messages = messageService.getAllUserMessages(id);
        return Response.ok(messages);
    }
}

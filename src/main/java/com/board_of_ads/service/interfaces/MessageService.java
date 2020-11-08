package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Message;
import com.board_of_ads.models.dto.MessageDto;
import java.util.List;

public interface MessageService {
    List<MessageDto> getAllUserMessages(Long id);
    void save(Message message);
}
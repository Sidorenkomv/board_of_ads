package com.board_of_ads.service.impl;

import com.board_of_ads.models.Message;
import com.board_of_ads.models.dto.MessageDto;
import com.board_of_ads.repository.MessageRepository;
import com.board_of_ads.service.interfaces.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<MessageDto> getAllUserMessages(Long id){
        return messageRepository.findAllUserMessages(id);
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}

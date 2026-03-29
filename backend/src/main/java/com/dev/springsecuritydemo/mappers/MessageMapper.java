package com.dev.springsecuritydemo.mappers;
import com.dev.springsecuritydemo.entities.Message;
import com.dev.springsecuritydemo.dto.MessageDTO;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MessageMapper {

    public MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getSenderId(),
                message.getReceiverId(),
                message.getText(),
                message.getDate(),
                message.getIsRead(),
                message.getMessageId()
        );
    }

    public List<MessageDTO> toDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toDTO)
                .toList();
    }
}


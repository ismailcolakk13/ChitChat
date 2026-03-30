package com.ismail.chitchat.mappers;

import org.springframework.stereotype.Component;

import com.ismail.chitchat.dto.MessageDTO;
import com.ismail.chitchat.entities.Message;
import com.ismail.chitchat.repositories.MyUserRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final MyUserRepository myUserRepository;

    public MessageDTO toDTO(Message message) {
        String senderUsername = myUserRepository.findById(message.getSenderId())
                .map(u -> u.getUsername())
                .orElse("Unknown");

        return new MessageDTO(
                senderUsername,
                message.getRoomId(),
                message.getText(),
                message.getDate(),
                message.getIsRead(),
                message.getMessageId());
    }

    public List<MessageDTO> toDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toDTO)
                .toList();
    }
}

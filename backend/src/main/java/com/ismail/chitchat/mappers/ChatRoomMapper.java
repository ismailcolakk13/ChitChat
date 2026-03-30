package com.ismail.chitchat.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ismail.chitchat.dto.ChatRoomDTO;
import com.ismail.chitchat.dto.MessageDTO;
import com.ismail.chitchat.entities.ChatRoom;
import com.ismail.chitchat.repositories.MessageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRoomMapper {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public ChatRoomDTO toDTO(ChatRoom chatRoom) {
        MessageDTO lastMessage = null;
        if (chatRoom.getRoomId() != null) {
            var message = messageRepository.findLastMessageByRoomId(chatRoom.getRoomId());
            if (message != null) {
                lastMessage = messageMapper.toDTO(message);
            }
        }

        return new ChatRoomDTO(
                chatRoom.getRoomId(),
                MyUserMapper.toDTOList(chatRoom.getUsers()),
                lastMessage);
    }

    public List<ChatRoomDTO> toDTOList(List<ChatRoom> chatRooms) {
        return chatRooms.stream().map(this::toDTO).toList();
    }
}

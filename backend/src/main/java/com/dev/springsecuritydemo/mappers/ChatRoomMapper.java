package com.dev.springsecuritydemo.mappers;
import com.dev.springsecuritydemo.entities.ChatRoom;
import com.dev.springsecuritydemo.dto.ChatRoomDTO;

import com.dev.springsecuritydemo.dto.MessageDTO;
import com.dev.springsecuritydemo.mappers.MessageMapper;
import com.dev.springsecuritydemo.repositories.MessageRepository;
import com.dev.springsecuritydemo.mappers.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


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
                lastMessage
        );
    }

    public List<ChatRoomDTO> toDTOList(List<ChatRoom> chatRooms) {
        return chatRooms.stream().map(this::toDTO).toList();
    }
}

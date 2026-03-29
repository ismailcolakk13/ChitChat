package com.dev.springsecuritydemo.services;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dev.springsecuritydemo.dto.ChatRoomDTO;
import com.dev.springsecuritydemo.dto.MessageDTO;
import com.dev.springsecuritydemo.entities.ChatRoom;
import com.dev.springsecuritydemo.entities.Message;
import com.dev.springsecuritydemo.entities.MyUser;
import com.dev.springsecuritydemo.mappers.ChatRoomMapper;
import com.dev.springsecuritydemo.mappers.MessageMapper;
import com.dev.springsecuritydemo.repositories.MessageRepository;
import com.dev.springsecuritydemo.repositories.MyUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MyUserRepository myUserRepository;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageMapper messageMapper;
    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomDTO sendMessage(Integer receiverId, String text) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser sender = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MyUser receiver = myUserRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getId().equals(receiver.getId()))
            throw new AccessDeniedException("You can't send message to yourself");

        ChatRoom chatRoom = chatRoomService.createOrGetChatRoom(sender.getId(), receiver.getId());

        Message message = Message.builder()
                .senderId(sender.getId())
                .receiverId(receiver.getId())
                .text(text)
                .chatRoom(chatRoom)
                .date(LocalDateTime.now())
                .isRead(false)
                .build();
        Message saved = messageRepository.save(message);

        // WebSocket addition
        MessageDTO dto = messageMapper.toDTO(saved);
        messagingTemplate.convertAndSend("/topic/rooms/" + chatRoom.getRoomId(), dto);

        // My additions

        return chatRoomMapper.toDTO(chatRoom);
    }

    public void sendMessageToRoom(Integer roomId, String username, String text) {
        MyUser sender = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom chatRoom = chatRoomService.getChatRoomById(roomId);

        Message message = Message.builder()
                .senderId(sender.getId())
                .text(text)
                .chatRoom(chatRoom)
                .date(LocalDateTime.now())
                .isRead(false)
                .build();
        Message saved = messageRepository.save(message);

        // WebSocket addition
        MessageDTO dto = messageMapper.toDTO(saved);
        messagingTemplate.convertAndSend("/topic/rooms/" + chatRoom.getRoomId(), dto);
    }

    public void updateMessage(Long messageId, String newText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser sender = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = messageRepository.findById(messageId).orElseThrow();
        if (!message.getSenderId().equals(sender.getId())) {
            throw new AccessDeniedException("You can't update this message");
        }
        message.setText(newText);

        messageRepository.save(message);
    }

    public void deleteMessage(Long messageId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser sender = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = messageRepository.findById(messageId).orElseThrow();
        if (!message.getSenderId().equals(sender.getId())) {
            throw new AccessDeniedException("You can't delete this message");
        }
        messageRepository.delete(message);
    }
}

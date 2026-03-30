package com.ismail.chitchat.services;

import java.time.LocalDateTime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ismail.chitchat.dto.ChatRoomDTO;
import com.ismail.chitchat.dto.MessageDTO;
import com.ismail.chitchat.entities.ChatRoom;
import com.ismail.chitchat.entities.Message;
import com.ismail.chitchat.entities.MyUser;
import com.ismail.chitchat.mappers.ChatRoomMapper;
import com.ismail.chitchat.mappers.MessageMapper;
import com.ismail.chitchat.repositories.MessageRepository;
import com.ismail.chitchat.repositories.MyUserRepository;

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

    public ChatRoomDTO sendMessage(String receiverIdentifier, String text) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        MyUser sender = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MyUser receiver;
        if (receiverIdentifier.contains("@")) {
            receiver = myUserRepository.findByEmail(receiverIdentifier)
                    .orElseThrow(() -> new RuntimeException("Receiver with this email not found"));
        } else {
            receiver = myUserRepository.findByPhone(receiverIdentifier)
                    .orElseThrow(() -> new RuntimeException("Receiver with this phone not found"));
        }

        if (sender.getId().equals(receiver.getId()))
            throw new AccessDeniedException("You can't send message to yourself");

        ChatRoom chatRoom = chatRoomService.createOrGetChatRoom(sender.getId(), receiver.getId());

        Message message = Message.builder()
                .senderId(sender.getId())
                .roomId(chatRoom.getRoomId())
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

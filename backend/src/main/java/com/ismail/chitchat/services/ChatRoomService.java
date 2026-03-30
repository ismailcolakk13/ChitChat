package com.ismail.chitchat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ismail.chitchat.entities.ChatRoom;
import com.ismail.chitchat.entities.Message;
import com.ismail.chitchat.entities.MyUser;
import com.ismail.chitchat.repositories.ChatRoomRepository;
import com.ismail.chitchat.repositories.MyUserRepository;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MyUserRepository myUserRepository;

    public boolean hasTheyChatRoom(Integer senderId, Integer receiverId) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByUsers(senderId, receiverId);
        return chatRoom != null;
    }

    public ChatRoom getChatRoomById(Integer roomId) {
        return chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public ChatRoom createOrGetChatRoom(Integer senderId, Integer receiverId) {
        ChatRoom existingRoom = chatRoomRepository.findChatRoomByUsers(senderId, receiverId);
        if (existingRoom != null) {
            return existingRoom;
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .users(List.of(
                        myUserRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found")),
                        myUserRepository.findById(receiverId)
                                .orElseThrow(() -> new RuntimeException("Receiver not found"))))
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public List<ChatRoom> getUsersChatRooms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MyUser currentUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatRoomRepository.findByUsers_Id(currentUser.getId());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Message> getMessagesByChatRoom(Integer roomId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        MyUser currentUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        boolean isMember = chatRoom.getUsers().stream().anyMatch(user -> user.getId().equals(currentUser.getId()));
        if (!isMember) {
            throw new AccessDeniedException("User is not authorized to enter this room");
        }

        chatRoom.getMessages().size(); // Initialize the proxy inside the transaction
        return chatRoom.getMessages();
    }

}

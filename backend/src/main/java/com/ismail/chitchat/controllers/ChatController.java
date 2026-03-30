package com.ismail.chitchat.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ismail.chitchat.dto.ChatRoomDTO;
import com.ismail.chitchat.dto.MessageDTO;
import com.ismail.chitchat.dto.NewMessageRequestDTO;
import com.ismail.chitchat.entities.ChatRoom;
import com.ismail.chitchat.entities.Message;
import com.ismail.chitchat.mappers.ChatRoomMapper;
import com.ismail.chitchat.mappers.MessageMapper;
import com.ismail.chitchat.services.ChatRoomService;
import com.ismail.chitchat.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ChatRoomMapper chatRoomMapper;

    // MessageCONTROLLER
    @PostMapping("/send")
    public ResponseEntity<ChatRoomDTO> sendMessage(@Valid @RequestBody NewMessageRequestDTO request) {
        ChatRoomDTO res = messageService.sendMessage(request.receiverIdentifier(), request.text());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDTO>> getUsersChatRooms() {
        List<ChatRoom> rooms = chatRoomService.getUsersChatRooms();
        return ResponseEntity.ok(chatRoomMapper.toDTOList(rooms));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDTO>> getRoomMessages(@PathVariable Integer roomId) {
        List<Message> messages = chatRoomService.getMessagesByChatRoom(roomId);
        List<MessageDTO> messagesDTO = messageMapper.toDTOList(messages);
        return ResponseEntity.ok(messagesDTO);
    }

    // MessageCONTROLLER
    @PutMapping("/update/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable Long messageId, @RequestBody MessageDTO messageDto) {
        messageService.updateMessage(messageId, messageDto.text());
        return ResponseEntity.ok("Message updated");
    }

    // MessageCONTROLLER
    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

}

package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.entities.ChatRoom;
import com.dev.springsecuritydemo.dto.ChatRoomDTO;
import com.dev.springsecuritydemo.mappers.ChatRoomMapper;
import com.dev.springsecuritydemo.services.ChatRoomService;
import com.dev.springsecuritydemo.entities.Message;
import com.dev.springsecuritydemo.dto.MessageDTO;
import com.dev.springsecuritydemo.mappers.MessageMapper;
import com.dev.springsecuritydemo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ChatRoomMapper chatRoomMapper;

    //MessageCONTROLLER
    @PostMapping("/send")
    public ResponseEntity<ChatRoomDTO> sendMessage(@RequestBody MessageDTO messageDto) {
        ChatRoomDTO res = messageService.sendMessage(messageDto.receiverId(),messageDto.text());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDTO>> getUsersChatRooms() {
        List<ChatRoom> rooms = chatRoomService.getUsersChatRooms();
        return ResponseEntity.ok(chatRoomMapper.toDTOList(rooms));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDTO>> getRoomMessages(@PathVariable Integer roomId) throws AccessDeniedException {
        List<Message> messages = chatRoomService.getMessagesByChatRoom(roomId);
        List<MessageDTO> messagesDTO= messageMapper.toDTOList(messages);
        return ResponseEntity.ok(messagesDTO);
    }

    //MessageCONTROLLER
    @PutMapping("/update/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable Long messageId, @RequestBody MessageDTO messageDto) {
        messageService.updateMessage(messageId,messageDto.text());
        return ResponseEntity.ok("Message updated");
    }

    //MessageCONTROLLER
    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }


}

package com.dev.springsecuritydemo.controllers;

import com.dev.springsecuritydemo.dto.MessageDTO;
import com.dev.springsecuritydemo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat.send/{roomId}")
    public void onMessage(@DestinationVariable Integer roomId, MessageDTO incomingMessage, java.security.Principal principal) {
        if (principal == null) throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        messageService.sendMessageToRoom(roomId, principal.getName(), incomingMessage.text());
    }
}

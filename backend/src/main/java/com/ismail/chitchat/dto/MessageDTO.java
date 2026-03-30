package com.ismail.chitchat.dto;

import java.time.LocalDateTime;

public record MessageDTO(String senderUsername, Integer roomId, String text, LocalDateTime date, Boolean isRead,
                Long messageId) {
}

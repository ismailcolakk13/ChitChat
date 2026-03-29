package com.dev.springsecuritydemo.dto;

import java.util.List;

public record ChatRoomDTO(Integer roomId, List<MyUserDTO> users, MessageDTO lastMessage) {
}

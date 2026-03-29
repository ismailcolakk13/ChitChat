package com.dev.springsecuritydemo.dto;

import com.dev.springsecuritydemo.dto.MessageDTO;
import com.dev.springsecuritydemo.dto.MyUserDTO;

import java.util.List;

public record ChatRoomDTO (Integer roomId, List<MyUserDTO> users, MessageDTO lastMessage) {
}

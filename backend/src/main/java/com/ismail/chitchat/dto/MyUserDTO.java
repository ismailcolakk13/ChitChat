package com.ismail.chitchat.dto;

import com.ismail.chitchat.entities.Role;

public record MyUserDTO(String username, String phone, Integer age, Role role) {

}

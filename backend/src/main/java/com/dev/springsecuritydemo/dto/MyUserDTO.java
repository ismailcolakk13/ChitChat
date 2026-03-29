package com.dev.springsecuritydemo.dto;
import com.dev.springsecuritydemo.entities.Role;

public record MyUserDTO(Integer id, String username, Integer age, Role role) {

}

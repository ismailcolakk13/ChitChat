package com.dev.springsecuritydemo.dto;

import com.dev.springsecuritydemo.dto.MyUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private MyUserDTO user;
    private String token;
}

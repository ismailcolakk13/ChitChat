package com.dev.springsecuritydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @jakarta.validation.constraints.NotBlank
    private String username;
    @jakarta.validation.constraints.NotBlank
    private String password;
}

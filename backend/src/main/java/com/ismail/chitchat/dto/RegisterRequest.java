package com.ismail.chitchat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @jakarta.validation.constraints.NotBlank(message = "Username cannot be empty")
    @jakarta.validation.constraints.Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @jakarta.validation.constraints.NotBlank(message = "Password cannot be empty")
    @jakarta.validation.constraints.Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    @jakarta.validation.constraints.NotNull(message = "Age is required")
    private Integer age;
    @jakarta.validation.constraints.NotBlank(message = "Phone cannot be empty")
    private String phone;
    @jakarta.validation.constraints.Email(message = "Invalid email format")
    @jakarta.validation.constraints.NotBlank(message = "Email cannot be empty")
    private String email;
}

package com.ismail.chitchat.dto;

import jakarta.validation.constraints.NotBlank;

public record NewMessageRequestDTO(
                @NotBlank(message = "Receiver identifier cannot be empty") String receiverIdentifier,
                @NotBlank(message = "Message text cannot be empty") String text) {
}

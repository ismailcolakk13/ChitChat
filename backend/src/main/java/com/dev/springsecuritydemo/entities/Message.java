package com.dev.springsecuritydemo.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private Integer senderId;
    private Integer receiverId;
    private String text;
    private LocalDateTime date;
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties({ "users", "messages" })
    private ChatRoom chatRoom;
}

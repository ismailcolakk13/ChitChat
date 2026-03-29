package com.dev.springsecuritydemo.entities;

import com.dev.springsecuritydemo.entities.ChatRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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
    @JsonIgnoreProperties({"users","messages"})
    private ChatRoom chatRoom;
}

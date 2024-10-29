package com.betmanager.models.dtos;

import com.betmanager.models.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {

    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;
}

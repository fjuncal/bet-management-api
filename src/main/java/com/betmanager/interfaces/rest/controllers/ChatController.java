package com.betmanager.interfaces.rest.controllers;


import com.betmanager.models.dtos.ChatMessage;
import com.betmanager.models.enums.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }
}

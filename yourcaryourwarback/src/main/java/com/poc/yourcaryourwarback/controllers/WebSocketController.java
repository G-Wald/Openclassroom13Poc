package com.poc.yourcaryourwarback.controllers;

import com.poc.yourcaryourwarback.models.ChatMessage;
import com.poc.yourcaryourwarback.models.ChatMessageSend;
import com.poc.yourcaryourwarback.models.User;
import com.poc.yourcaryourwarback.services.MessageService;
import com.poc.yourcaryourwarback.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class WebSocketController {

    private final MessageService messageService;
    private final UserService userService;

    public WebSocketController(UserService userService, MessageService messageService ) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessageSend handleChatMessage(ChatMessageSend message) {
        User sender = userService.findByUsernameOrEmail(message.getSenderUsername());
        User receiver = userService.findByUsernameOrEmail(message.getReceiverUsername());

        if(sender == null || receiver == null) {
            return null;
        }
        messageService.save(new ChatMessage(sender.getId(), receiver.getId(), message.getMessageText()));

        return message;
    }

}
